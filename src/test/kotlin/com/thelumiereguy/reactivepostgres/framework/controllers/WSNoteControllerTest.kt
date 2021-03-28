/*
 * Created by Piyush Pradeepkumar on 28/03/21, 11:29 AM.
 *  Copyright (c) 2021 People Interactive. All rights reserved.
 */

package com.thelumiereguy.reactivepostgres.framework.controllers

import com.thelumiereguy.reactivepostgres.config.AppURLs
import com.thelumiereguy.reactivepostgres.config.AppURLs.applicationEndpoint
import com.thelumiereguy.reactivepostgres.config.AppURLs.notesSubscriptionTopic
import com.thelumiereguy.reactivepostgres.config.AppURLs.stompBrokerEndpoint
import com.thelumiereguy.reactivepostgres.config.AppURLs.updateEndpoint
import com.thelumiereguy.reactivepostgres.presentation.dto.note.ActionType
import com.thelumiereguy.reactivepostgres.presentation.dto.note.GetNotesResponseDTO
import com.thelumiereguy.reactivepostgres.presentation.dto.note.Note
import com.thelumiereguy.reactivepostgres.presentation.dto.note.NoteRequestDTO
import com.thelumiereguy.reactivepostgres.presentation.wrapper.GenericResponseDTOWrapper
import org.assertj.core.api.Assertions
import org.hildan.jackstomp.JackstompClient
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
internal class WSNoteControllerTest @Autowired constructor(
    val client: WebTestClient
) {

    @LocalServerPort
    var port: Int = 0

    val WEBSOCKET_URI by lazy {
        "ws://localhost:$port" + AppURLs.wsEndpoint
    }

    lateinit var stompClient: JackstompClient

    @BeforeEach
    fun setup() {
        stompClient = JackstompClient()
    }

    @Test
    fun `test websocket for basic push and subscribe`() {
        val session =
            stompClient.syncConnect(WEBSOCKET_URI)

        val channel = session.subscribe(stompBrokerEndpoint + notesSubscriptionTopic, Note::class.java)

        val noteRequestDTO = NoteRequestDTO(
            Note("test", "test", "test", System.currentTimeMillis()),
            ActionType.CREATE
        )

        session.send(applicationEndpoint + updateEndpoint, noteRequestDTO)

        val note = channel.next()

        assertEquals(noteRequestDTO.note, note)
    }

    @Test
    fun `when note is pushed to socket, it must be updated to db`() {
        val session =
            stompClient.syncConnect(WEBSOCKET_URI)

        val channel = session.subscribe(stompBrokerEndpoint + notesSubscriptionTopic, Note::class.java)

        val noteRequestDTO = NoteRequestDTO(
            Note("test", "test", "test", System.currentTimeMillis()),
            ActionType.CREATE
        )
        session.send(applicationEndpoint + updateEndpoint, noteRequestDTO)
        val note = channel.next()
        assertEquals(noteRequestDTO.note, note)


        //Get all notes
        client.get()
            .uri(AppURLs.baseURL + AppURLs.getNotes)
            .exchange()
            .expectStatus().isOk
            .expectBody<GenericResponseDTOWrapper<GetNotesResponseDTO>>()
            .consumeWith {
                it.responseBody?.data?.notes?.let {
                    Assertions.assertThat(it).isNotEmpty
                    Assertions.assertThat(it).contains(noteRequestDTO.note)
                }
            }
    }
}