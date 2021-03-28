/*
 * Created by Piyush Pradeepkumar on 28/03/21, 9:46 PM.
 *  Copyright (c) 2021 People Interactive. All rights reserved.
 */

package com.thelumiereguy.reactivepostgres.data.notes.entity


data class NoteEntity(
    val title: String? = null,
    val content: String? = null,
    val created_by: String? = null,
    val created_on: Long? = null,
    val id: Long = 0
)