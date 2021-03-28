/*
 * Created by Piyush Pradeepkumar on 27/03/21, 11:39 PM.
 *  Copyright (c) 2021 People Interactive. All rights reserved.
 */

package com.thelumiereguy.reactivepostgres.framework.controllers

import com.thelumiereguy.reactivepostgres.config.AppURLs
import com.thelumiereguy.reactivepostgres.presentation.dto.GetNotesResponseDTO
import com.thelumiereguy.reactivepostgres.presentation.wrapper.GenericDTOWrapper
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
    @DisplayName(AppURLs.getNotes)
    inner class GetNotes {

        @Test
        fun `should return all Notes`() {
            client.get()
                .uri(AppURLs.baseURL + AppURLs.getNotes)
                .exchange()
                .expectStatus().isOk
                .expectBody<GenericDTOWrapper<GetNotesResponseDTO>>()
                .consumeWith {
                    it.responseBody?.data?.notes?.forEach {
                        Assertions.assertThat(it.id).isNotZero
                        Assertions.assertThat(it.created_by).isNotEmpty
                        Assertions.assertThat(it.editors).isNotEmpty()
                    }
                }
        }
    }
}