package com.thelumiereguy.reactivepostgres.presentation.dto

data class GetNotesResponseDTO(val notes: List<Notes>)

data class Notes(
    val id: Long,
    val editors: List<String>,
    val title: String,
    val content: String,
    val created_by: String,
    val created_on: Long
)
