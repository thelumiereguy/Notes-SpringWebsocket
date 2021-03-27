/*
 * Created by Piyush Pradeepkumar on 27/03/21, 11:39 PM.
 *  Copyright (c) 2021 People Interactive. All rights reserved.
 */

package com.thelumiereguy.reactivepostgres.framework.controller

import com.thelumiereguy.reactivepostgres.config.AppURL
import com.thelumiereguy.reactivepostgres.config.NotesURL
import com.thelumiereguy.reactivepostgres.presentation.dto.GetNotesResponseDTO
import com.thelumiereguy.reactivepostgres.presentation.wrapper.GenericDataWrapper
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
internal class NotesControllerTest @Autowired constructor(
    val client: WebTestClient
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
                .expectStatus().isOk
                .expectBody<GenericDataWrapper<GetNotesResponseDTO>>()
                .consumeWith {
                    Assertions.assertThat(it.responseBody?.data?.notes).isEmpty()
                }
        }
    }
}