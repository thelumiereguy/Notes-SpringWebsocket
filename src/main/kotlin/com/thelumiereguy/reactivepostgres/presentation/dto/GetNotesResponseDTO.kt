package com.thelumiereguy.reactivepostgres.presentation.dto

data class GetNotesResponseDTO(val notes: List<Note>)

data class Note(
    val editors: List<String>,
    val title: String,
    val content: String,
    val created_by: String,
    val created_on: Long,

    val id: Long = 0,
)
