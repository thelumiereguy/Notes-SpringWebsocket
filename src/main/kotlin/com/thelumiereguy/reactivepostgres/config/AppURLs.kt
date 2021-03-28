package com.thelumiereguy.reactivepostgres.config

object AppURLs {
    const val baseURL = "/api"
    const val getNotes = "/getNotes"

    const val wsEndpoint = "/ws"
    const val applicationEndpoint = "/app"


    const val updateEndpoint = "/updateNote"

    const val stompBrokerEndpoint = "/topic"
    const val notesSubscriptionTopic = ".notes"
}