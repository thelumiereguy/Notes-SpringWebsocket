package com.thelumiereguy.reactivepostgres.notes.controller

import com.thelumiereguy.reactivepostgres.app.config.AppURL
import com.thelumiereguy.reactivepostgres.notes.controller.config.NotesURL
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.web.reactive.server.WebTestClient

@WebFluxTest
internal class NotesControllerTest(
    @Autowired private val client: WebTestClient
) {

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName(NotesURL.getNotes)
    inner class GetNotes {

        @Test
        fun `should return all Notes`() {
            client.get()
                .uri(AppURL.baseURL + NotesURL.getNotes)
                .exchange()
                .expectBody()

        }

    }
}