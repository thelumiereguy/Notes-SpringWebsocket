/*
 * Created by Piyush Pradeepkumar on 28/03/21, 9:46 PM.
 *  Copyright (c) 2021 People Interactive. All rights reserved.
 */

package com.thelumiereguy.reactivepostgres.presentation.dto.note

import com.fasterxml.jackson.annotation.JsonProperty

data class NoteRequestDTO(
    @JsonProperty("note")
    val note: Note,
    @JsonProperty("actionType")
    val actionType: ActionType
)


enum class ActionType {
    CREATE,
    DELETE,
    UPDATE
}