/*
 * Created by Piyush Pradeepkumar on 27/03/21, 11:39 PM.
 *  Copyright (c) 2021 People Interactive. All rights reserved.
 */

package com.thelumiereguy.reactivepostgres.framework.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.thelumiereguy.reactivepostgres.config.AppURL
import com.thelumiereguy.reactivepostgres.config.NotesURL
import com.thelumiereguy.reactivepostgres.presentation.dto.GetNotesResponseDTO
import com.thelumiereguy.reactivepostgres.presentation.wrapper.GenericDataWrapper
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.expectBody
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest
@AutoConfigureMockMvc
internal class NotesControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
) {

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    @DisplayName(NotesURL.getNotes)
    inner class GetNotes {

        val response = GenericDataWrapper(GetNotesResponseDTO(emptyList()))
        @Test
        fun `should return all Notes`() {
            mockMvc.get(AppURL.baseURL + NotesURL.getNotes)
                .andDo {
                    print()
                }.andExpect {
                    status {
                        isOk()
                        content {
                            string(objectMapper.writeValueAsString(response))
                            contentType(MediaType.APPLICATION_JSON)
                        }

                    }
                }
        }
    }
}