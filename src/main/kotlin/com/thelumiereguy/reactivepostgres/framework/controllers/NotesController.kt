/*
 * Created by Piyush Pradeepkumar on 27/03/21, 11:27 PM.
 *  Copyright (c) 2021 People Interactive. All rights reserved.
 */

package com.thelumiereguy.reactivepostgres.framework.controllers

import com.thelumiereguy.reactivepostgres.config.AppURLs
import com.thelumiereguy.reactivepostgres.config.successCreateMessage
import com.thelumiereguy.reactivepostgres.config.successDeleteMessage
import com.thelumiereguy.reactivepostgres.config.successUpdateMessage
import com.thelumiereguy.reactivepostgres.presentation.dto.note.*
import com.thelumiereguy.reactivepostgres.presentation.wrapper.GenericResponseDTOWrapper
import com.thelumiereguy.reactivepostgres.presentation.wrapper.wrap
import com.thelumiereguy.reactivepostgres.usecases.get_notes.GetNotes
import com.thelumiereguy.reactivepostgres.usecases.update_note.create.CreateNote
import com.thelumiereguy.reactivepostgres.usecases.update_note.delete.DeleteNote
import com.thelumiereguy.reactivepostgres.usecases.update_note.update.UpdateNote
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping(AppURLs.baseURL)
class NotesController @Autowired constructor(
    private val getNotes: GetNotes,
    private val createNoteUseCase: CreateNote,
    private val deleteNoteUseCase: DeleteNote,
    private val updateNoteUseCase: UpdateNote,
    private val brokerMessagingTemplate: SimpMessagingTemplate
) {

    /**
     * @return List of all notes
     */
    @GetMapping(AppURLs.getNotes)
    suspend fun getAllNotes(): GenericResponseDTOWrapper<GetNotesResponseDTO> {
        return GenericResponseDTOWrapper(GetNotesResponseDTO(notes = getNotes()))
    }

    @DeleteMapping(AppURLs.deleteNote + "/{noteID}")
    @ResponseStatus(HttpStatus.OK)
    suspend fun deleteNote(@PathVariable noteID: Long): GenericResponseDTOWrapper<UpdateResponseDTO> {
        val note = deleteNoteUseCase(noteID)
        brokerMessagingTemplate.convertAndSend(
            AppURLs.stompBrokerEndpoint + AppURLs.notesSubscriptionTopic,
            NotesUpdateEventDTO(note, UpdateType.deleted.name)
        )
        return wrap(UpdateResponseDTO(message = successDeleteMessage, note))
    }


    @PostMapping(AppURLs.createNote)
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun createNote(@RequestBody requestDTO: Note): GenericResponseDTOWrapper<UpdateResponseDTO> {
        val note = createNoteUseCase(requestDTO)
        brokerMessagingTemplate.convertAndSend(
            AppURLs.stompBrokerEndpoint + AppURLs.notesSubscriptionTopic,
            NotesUpdateEventDTO(note, UpdateType.created.name)
        )
        return wrap(UpdateResponseDTO(message = successCreateMessage, note))
    }


    @PutMapping(AppURLs.updateNote)
    @ResponseStatus(HttpStatus.OK)
    suspend fun updateNote(@RequestBody requestDTO: Note): GenericResponseDTOWrapper<UpdateResponseDTO> {
        val note = updateNoteUseCase(requestDTO)
        brokerMessagingTemplate.convertAndSend(
            AppURLs.stompBrokerEndpoint + AppURLs.notesSubscriptionTopic,
            NotesUpdateEventDTO(note, UpdateType.updated.name)
        )
        return wrap(UpdateResponseDTO(message = successUpdateMessage, note))
    }
}