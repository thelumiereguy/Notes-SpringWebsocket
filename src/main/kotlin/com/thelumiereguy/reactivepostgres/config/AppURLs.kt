package com.thelumiereguy.reactivepostgres.config

object AppURLs {
    const val baseURL = "/api"
    const val getNotes = "/getNotes"
    const val updateNote = "/note"

    const val wsEndpoint = "/ws"
    const val applicationEndpoint = "/app"

    const val stompBrokerEndpoint = "/topic"
    const val notesSubscriptionTopic = ".notes"
}