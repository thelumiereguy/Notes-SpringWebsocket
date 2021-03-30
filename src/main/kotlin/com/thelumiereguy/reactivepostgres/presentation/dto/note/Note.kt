/*
 * Created by Piyush Pradeepkumar on 28/03/21, 9:47 PM.
 *  Copyright (c) 2021 People Interactive. All rights reserved.
 */

package com.thelumiereguy.reactivepostgres.presentation.dto.note

import com.fasterxml.jackson.annotation.JsonProperty

data class Note(
    @JsonProperty("title")
    val title: String?,
    @JsonProperty("content")
    val content: String?,
    @JsonProperty("created_by")
    val created_by: String?,
    @JsonProperty("created_on")
    val created_on: Long?,
    @JsonProperty("id")
    val id: Long = 0,
)