/*
 * Created by Piyush Pradeepkumar on 27/03/21, 11:27 PM.
 *  Copyright (c) 2021 People Interactive. All rights reserved.
 */

package com.thelumiereguy.reactivepostgres.framework.controller

import com.thelumiereguy.reactivepostgres.config.AppURLs
import com.thelumiereguy.reactivepostgres.presentation.dto.GetNotesResponseDTO
import com.thelumiereguy.reactivepostgres.presentation.wrapper.GenericDTOWrapper
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(AppURLs.baseURL)
class NotesController {

    @GetMapping(AppURLs.getNotes)
    fun getNotes(): GenericDTOWrapper<GetNotesResponseDTO> {
        return GenericDTOWrapper(GetNotesResponseDTO(emptyList()))
    }

}