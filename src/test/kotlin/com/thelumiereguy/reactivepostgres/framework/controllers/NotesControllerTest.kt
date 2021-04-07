/*
 * Created by Piyush Pradeepkumar on 27/03/21, 11:39 PM.
 *  Copyright (c) 2021 People Interactive. All rights reserved.
 */

package com.thelumiereguy.reactivepostgres.framework.controllers

import com.thelumiereguy.reactivepostgres.config.AppURLs
import com.thelumiereguy.reactivepostgres.config.successCreateMessage
import com.thelumiereguy.reactivepostgres.config.successDeleteMessage
import com.thelumiereguy.reactivepostgres.config.successUpdateMessage
import com.thelumiereguy.reactivepostgres.presentation.dto.note.*
import com.thelumiereguy.reactivepostgres.presentation.wrapper.GenericResponseDTOWrapper
import org.assertj.core.api.Assertions.assertThat
import org.hildan.jackstomp.JackstompClient
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
internal class NotesControllerTest @Autowired constructor(
    val client: WebTestClient
) {

    val timeStamp = System.currentTimeMillis()

    @LocalServerPort
    var port: Int = 0

    private val WEBSOCKET_URI by lazy {
        "ws://localhost:$port" + AppURLs.wsEndpoint
    }

    lateinit var stompClient: JackstompClient

    @BeforeEach
    fun setup() {
        stompClient = JackstompClient()
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("GET ${AppURLs.getNotes}")
    inner class GetNotes {

        @Test
        fun `should return all Notes`() {
            client.get()
                .uri(AppURLs.baseURL + AppURLs.getNotes)
                .exchange()
                .expectStatus().isOk
                .expectBody<GenericResponseDTOWrapper<GetNotesResponseDTO>>()
                .consumeWith {
                    it.responseBody?.data?.notes?.forEach {
                        assertThat(it.id).isNotZero
                        assertThat(it.created_by).isNotEmpty
                    }
                }
        }
    }

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("POST ${AppURLs.createNote}")
    inner class CreateNote {

        @Test
        fun `should create new bank and add to db`() {

            val noteObj = Note("", "", "test_user", timeStamp)

            val session =
                stompClient.syncConnect(WEBSOCKET_URI)

            //Subscribe to socket for changes in notes
            val channel =
                session.subscribe(
                    AppURLs.stompBrokerEndpoint + AppURLs.notesSubscriptionTopic,
                    NotesUpdateEventDTO::class.java
                )


            //Add Note
            client.post()
                .uri(AppURLs.baseURL + AppURLs.createNote)
                .bodyValue(noteObj)
                .exchange()
                .expectStatus().isCreated
                .expectBody<GenericResponseDTOWrapper<UpdateResponseDTO>>()
                .consumeWith {
                    assertThat(it.responseBody).isNotNull
                    it.responseBody?.data?.let {
                        assertThat(it.message).isEqualTo(successCreateMessage)
                    }
                }

            //Get latest note pushed to socket
            val note = channel.next()

            assertEquals(noteObj, note.new_note)
            assertEquals(UpdateType.created.name, note.type)
        }

        @Test
        fun `basic validations for request body`() {

            val noteObj = Note("", "", "", timeStamp)

            //Add Note
            client.post()
                .uri(AppURLs.baseURL + AppURLs.createNote)
                .bodyValue(noteObj)
                .exchange()
                .expectStatus().isBadRequest

        }
    }


    @Nested
    @DirtiesContext
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("DELETE ${AppURLs.deleteNote}")
    inner class DeleteNote {

        @Test
        fun `should delete bank from db`() {

            val noteObj = Note("", "", "test_user", timeStamp, id = 10)

            val session =
                stompClient.syncConnect(WEBSOCKET_URI)

            //Subscribe to socket for changes in notes
            val channel =
                session.subscribe(
                    AppURLs.stompBrokerEndpoint + AppURLs.notesSubscriptionTopic,
                    NotesUpdateEventDTO::class.java
                )


            //Add Note
            client.post()
                .uri(AppURLs.baseURL + AppURLs.createNote)
                .bodyValue(noteObj)
                .exchange()
                .expectStatus().isCreated
                .expectBody<GenericResponseDTOWrapper<UpdateResponseDTO>>()
                .consumeWith {
                    assertThat(it.responseBody).isNotNull
                    it.responseBody?.data?.let {
                        assertThat(it.message).isEqualTo(successCreateMessage)
                    }
                }

            val note = channel.next()

            assertEquals(noteObj, note.new_note)
            assertEquals(UpdateType.created.name, note.type)

            //Delete note
            client.delete()
                .uri(AppURLs.baseURL + AppURLs.deleteNote + "/10")
                .exchange()
                .expectStatus().isOk
                .expectBody<GenericResponseDTOWrapper<UpdateResponseDTO>>()
                .consumeWith {
                    assertThat(it.responseBody).isNotNull
                    it.responseBody?.data?.let {
                        assertThat(it.message).isEqualTo(successDeleteMessage)
                    }
                }

            //Get latest note pushed to socket
            val note2 = channel.next()

            assertEquals(noteObj, note2.new_note)
            assertEquals(UpdateType.deleted.name, note2.type)
        }

        @Test
        fun `should throw not found if db doesnt contain the specific note`() {

            //Delete note
            client.delete()
                .uri(AppURLs.baseURL + AppURLs.deleteNote + "/10")
                .exchange()
                .expectStatus().isNotFound

        }
    }


    @Nested
    @DirtiesContext
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName("PUT ${AppURLs.updateNote}")
    inner class UpdateNote {

        @Test
        fun `should update bank in db`() {

            val noteObj = Note("test", "test", "test_user", timeStamp, id = 2)

            val session =
                stompClient.syncConnect(WEBSOCKET_URI)

            //Subscribe to socket for changes in notes
            val channel =
                session.subscribe(
                    AppURLs.stompBrokerEndpoint + AppURLs.notesSubscriptionTopic,
                    NotesUpdateEventDTO::class.java
                )


            //Add Note
            client.post()
                .uri(AppURLs.baseURL + AppURLs.createNote)
                .bodyValue(noteObj)
                .exchange()
                .expectStatus().isCreated
                .expectBody<GenericResponseDTOWrapper<UpdateResponseDTO>>()
                .consumeWith {
                    assertThat(it.responseBody).isNotNull
                    it.responseBody?.data?.let {
                        assertThat(it.message).isEqualTo(successCreateMessage)
                    }
                }

            val note = channel.next()

            assertEquals(noteObj, note.new_note)
            assertEquals(UpdateType.created.name, note.type)


            val updatedNote = noteObj.copy(title = "New_Title")

            //Update note
            client.put()
                .uri(AppURLs.baseURL + AppURLs.updateNote)
                .bodyValue(updatedNote)
                .exchange()
                .expectStatus().isOk
                .expectBody<GenericResponseDTOWrapper<UpdateResponseDTO>>()
                .consumeWith {
                    assertThat(it.responseBody).isNotNull
                    it.responseBody?.data?.let {
                        assertThat(it.message).isEqualTo(successUpdateMessage)
                    }
                }

            //Get latest note pushed to socket
            val note2 = channel.next()

            assertEquals(updatedNote, note2.new_note)
            assertEquals(UpdateType.updated.name, note2.type)
        }

        @Test
        fun `should throw not found if db doesnt contain the specific note`() {

            val noteObj = Note("test", "test", "test_user", timeStamp, id = 4)

            //Update note
            client.put()
                .uri(AppURLs.baseURL + AppURLs.updateNote)
                .bodyValue(noteObj)
                .exchange()
                .expectStatus().isNotFound

        }
    }
}