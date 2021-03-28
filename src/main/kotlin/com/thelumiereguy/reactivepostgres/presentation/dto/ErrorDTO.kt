package com.thelumiereguy.reactivepostgres.presentation.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class ErrorDTO(
    @JsonProperty("statusCode")
    val statusCode: Int,
    @JsonProperty("message")
    val message: String
)
