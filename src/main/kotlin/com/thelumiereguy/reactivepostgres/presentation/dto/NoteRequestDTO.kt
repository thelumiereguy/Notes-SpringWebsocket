/*
 * Created by Piyush Pradeepkumar on 28/03/21, 10:23 AM.
 *  Copyright (c) 2021 People Interactive. All rights reserved.
 */

package com.thelumiereguy.reactivepostgres.presentation.dto

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