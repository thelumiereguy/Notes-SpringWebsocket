/*
 * Created by Piyush Pradeepkumar on 28/03/21, 10:23 AM.
 *  Copyright (c) 2021 People Interactive. All rights reserved.
 */

package com.thelumiereguy.reactivepostgres.presentation.dto

data class NoteRequestDTO(
    val note: Note,
    val actionType: ActionType
)


enum class ActionType {
    CREATE,
    DELETE,
    UPDATE
}