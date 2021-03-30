/*
 * Created by Piyush Pradeepkumar on 27/03/21, 11:27 PM.
 *  Copyright (c) 2021 People Interactive. All rights reserved.
 */

package com.thelumiereguy.reactivepostgres.framework.controllers

import com.thelumiereguy.reactivepostgres.config.AppURLs
import com.thelumiereguy.reactivepostgres.config.successCreateMessage
import com.thelumiereguy.reactivepostgres.presentation.dto.note.GetNotesResponseDTO
import com.thelumiereguy.reactivepostgres.presentation.dto.note.Note
import com.thelumiereguy.reactivepostgres.presentation.dto.note.UpdateResponseDTO
import com.thelumiereguy.reactivepostgres.presentation.wrapper.GenericResponseDTOWrapper
import com.thelumiereguy.reactivepostgres.presentation.wrapper.wrap
import com.thelumiereguy.reactivepostgres.usecases.get_notes.GetNotes
import com.thelumiereguy.reactivepostgres.usecases.update_note.create.CreateNote
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(AppURLs.baseURL)
class NotesController @Autowired constructor(
    private val getNotes: GetNotes,
    private val createNoteUseCase: CreateNote,
    private val brokerMessagingTemplate: SimpMessagingTemplate
) {

    @GetMapping(AppURLs.getNotes)
    suspend fun getAllNotes(): GenericResponseDTOWrapper<GetNotesResponseDTO> {
        return GenericResponseDTOWrapper(GetNotesResponseDTO(notes = getNotes()))
    }

//    @DeleteMapping(AppURLs.updateNote)
//    suspend fun deleteNote(@RequestBody requestDTO: NoteRequestDTO): GenericResponseDTOWrapper<GetNotesResponseDTO> {
//        return GenericResponseDTOWrapper(GetNotesResponseDTO(notes = getNotes()))
//    }


    @PostMapping(AppURLs.updateNote)
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun createNote(@RequestBody requestDTO: Note): GenericResponseDTOWrapper<UpdateResponseDTO> {
        val note = createNoteUseCase(requestDTO)
        brokerMessagingTemplate.convertAndSend(AppURLs.stompBrokerEndpoint + AppURLs.notesSubscriptionTopic, note)
        return wrap(UpdateResponseDTO(message = successCreateMessage, note))
    }


//    @PutMapping(AppURLs.updateNote)
//    suspend fun updateNote(@RequestBody requestDTO: NoteRequestDTO): GenericResponseDTOWrapper<GetNotesResponseDTO> {
//        return GenericResponseDTOWrapper(GetNotesResponseDTO(notes = getNotes()))
//    }
}