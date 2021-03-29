/*
 * Created by Piyush Pradeepkumar on 28/03/21, 10:21 AM.
 *  Copyright (c) 2021 People Interactive. All rights reserved.
 */

package com.thelumiereguy.reactivepostgres.framework.controllers

import com.thelumiereguy.reactivepostgres.config.AppURLs
import com.thelumiereguy.reactivepostgres.config.AppURLs.notesSubscriptionTopic
import com.thelumiereguy.reactivepostgres.config.AppURLs.stompBrokerEndpoint
import com.thelumiereguy.reactivepostgres.presentation.dto.note.Note
import com.thelumiereguy.reactivepostgres.presentation.dto.note.NoteRequestDTO
import com.thelumiereguy.reactivepostgres.usecases.update_note.create.CreateNote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller

@Controller
class WSNoteController @Autowired constructor(
    private val createNote: CreateNote,
    private val brokerMessagingTemplate: SimpMessagingTemplate
) {

    @MessageMapping(AppURLs.updateEndpoint)
    @SendTo(stompBrokerEndpoint + notesSubscriptionTopic)
    fun updateNote(@Payload requestDTO: NoteRequestDTO) {
        CoroutineScope(Dispatchers.IO).launch {
            val note = createNote(requestDTO.note)
            brokerMessagingTemplate.convertAndSend(stompBrokerEndpoint + notesSubscriptionTopic, note)
        }
    }

}