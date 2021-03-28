/*
 * Created by Piyush Pradeepkumar on 28/03/21, 11:29 AM.
 *  Copyright (c) 2021 People Interactive. All rights reserved.
 */

package com.thelumiereguy.reactivepostgres.framework.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.thelumiereguy.reactivepostgres.config.AppURLs
import com.thelumiereguy.reactivepostgres.config.AppURLs.applicationEndpoint
import com.thelumiereguy.reactivepostgres.config.AppURLs.notesSubscriptionTopic
import com.thelumiereguy.reactivepostgres.config.AppURLs.stompBrokerEndpoint
import com.thelumiereguy.reactivepostgres.config.AppURLs.updateEndpoint
import com.thelumiereguy.reactivepostgres.presentation.dto.ActionType
import com.thelumiereguy.reactivepostgres.presentation.dto.Note
import com.thelumiereguy.reactivepostgres.presentation.dto.NoteRequestDTO
import org.hildan.jackstomp.JackstompClient
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.messaging.converter.MappingJackson2MessageConverter


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class WSNoteControllerTest(
    @Autowired private val objectMapper: ObjectMapper
) {

    @LocalServerPort
    var port: Int = 0

    val WEBSOCKET_URI by lazy {
        "ws://localhost:$port" + AppURLs.wsEndpoint
    }

    lateinit var stompClient: JackstompClient

    @BeforeEach
    fun setup() {
        stompClient = JackstompClient().apply {
            webSocketClient.messageConverter = MappingJackson2MessageConverter()
        }

    }

    @Test
    fun `test basic update publish `() {
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
}