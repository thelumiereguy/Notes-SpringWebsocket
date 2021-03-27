/*
 * Created by Piyush Pradeepkumar on 27/03/21, 11:27 PM.
 *  Copyright (c) 2021 People Interactive. All rights reserved.
 */

package com.thelumiereguy.reactivepostgres.framework.controller

import com.thelumiereguy.reactivepostgres.config.AppURL
import com.thelumiereguy.reactivepostgres.config.NotesURL
import com.thelumiereguy.reactivepostgres.presentation.dto.GetNotesResponseDTO
import com.thelumiereguy.reactivepostgres.presentation.wrapper.GenericDataWrapper
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(AppURL.baseURL)
class NotesController {

    @GetMapping(NotesURL.getNotes)
    fun getNotes(): GenericDataWrapper<GetNotesResponseDTO> {
        return GenericDataWrapper(GetNotesResponseDTO(emptyList()))
    }

}