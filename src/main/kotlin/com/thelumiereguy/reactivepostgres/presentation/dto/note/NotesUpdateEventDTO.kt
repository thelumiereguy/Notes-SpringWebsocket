/*
 * Created by Piyush Pradeepkumar on 28/03/21, 9:47 PM.
 *  Copyright (c) 2021 People Interactive. All rights reserved.
 */

package com.thelumiereguy.reactivepostgres.presentation.dto.note

import com.fasterxml.jackson.annotation.JsonProperty

data class NotesUpdateEventDTO(
    @JsonProperty("new_note")
    val new_note: Note,
    @JsonProperty("type")
    val type: String
)

enum class UpdateType {
    created,
    deleted,
    updated
}
