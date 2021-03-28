/*
 * Created by Piyush Pradeepkumar on 28/03/21, 10:21 AM.
 *  Copyright (c) 2021 People Interactive. All rights reserved.
 */

package com.thelumiereguy.reactivepostgres.framework.controller

import com.thelumiereguy.reactivepostgres.config.AppURLs
import com.thelumiereguy.reactivepostgres.config.AppURLs.notesSubscriptionTopic
import com.thelumiereguy.reactivepostgres.config.AppURLs.stompBrokerEndpoint
import com.thelumiereguy.reactivepostgres.presentation.dto.Note
import com.thelumiereguy.reactivepostgres.presentation.dto.NoteRequestDTO
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller

@Controller
class WSNoteController {

    @MessageMapping(AppURLs.updateEndpoint)
    @SendTo(stompBrokerEndpoint + notesSubscriptionTopic)
    fun sendMessage(@Payload requestDTO: NoteRequestDTO): Note {
        println("Welcome ${requestDTO.note.title}")
        return requestDTO.note
    }

}