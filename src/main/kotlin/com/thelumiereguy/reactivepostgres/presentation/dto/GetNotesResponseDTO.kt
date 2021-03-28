package com.thelumiereguy.reactivepostgres.presentation.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class GetNotesResponseDTO(val notes: List<Note>)

data class Note(
    @JsonProperty("title")
    val title: String?,
    @JsonProperty("content")
    val content: String?,
    @JsonProperty("created_by")
    val created_by: String,
    @JsonProperty("created_on")
    val created_on: Long,
    @JsonProperty("id")
    val id: Long = 0,
)
