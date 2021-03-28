/*
 * Created by Piyush Pradeepkumar on 27/03/21, 11:27 PM.
 *  Copyright (c) 2021 People Interactive. All rights reserved.
 */

package com.thelumiereguy.reactivepostgres.framework.controllers

import com.thelumiereguy.reactivepostgres.config.AppURLs
import com.thelumiereguy.reactivepostgres.presentation.dto.note.GetNotesResponseDTO
import com.thelumiereguy.reactivepostgres.presentation.wrapper.GenericResponseDTOWrapper
import com.thelumiereguy.reactivepostgres.usecases.get_notes.GetNotes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(AppURLs.baseURL)
class NotesController constructor(@Autowired private val getNotes: GetNotes) {

    @GetMapping(AppURLs.getNotes)
    suspend fun getAllNotes(): GenericResponseDTOWrapper<GetNotesResponseDTO> {
        return GenericResponseDTOWrapper(GetNotesResponseDTO(notes = getNotes()))
    }

}