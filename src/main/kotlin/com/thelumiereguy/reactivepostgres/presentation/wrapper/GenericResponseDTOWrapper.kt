package com.thelumiereguy.reactivepostgres.presentation.wrapper

data class GenericResponseDTOWrapper<T>(val data: T)

fun <T> wrap(data: T) = GenericResponseDTOWrapper(data)