package com.thelumiereguy.reactivepostgres.presentation.dto

data class GetNotesResponseDTO(val notes: List<Note>)

data class Note(
    val title: String?,
    val content: String?,
    val created_by: String,
    val created_on: Long,
    val editors: List<String>,

    val id: Long = 0,
)
