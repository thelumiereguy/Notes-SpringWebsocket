package com.thelumiereguy.reactivepostgres.notes.controller

import com.thelumiereguy.reactivepostgres.app.config.AppURL
import com.thelumiereguy.reactivepostgres.notes.controller.config.NotesURL
import kotlinx.coroutines.flow.flow
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyAndAwait
import org.springframework.web.reactive.function.server.coRouter

@RestController
class NotesController {

    @Bean
    fun mainRouter(handler: NotesController) = coRouter {
        AppURL.baseURL.nest {
            accept(MediaType.APPLICATION_JSON).nest {
                GET(NotesURL.getNotes).invoke(handler::getNotes)

            }
        }
    }


    private suspend fun getNotes(request: ServerRequest): ServerResponse {
        return ServerResponse.ok().bodyAndAwait(flow {  })
    }
}