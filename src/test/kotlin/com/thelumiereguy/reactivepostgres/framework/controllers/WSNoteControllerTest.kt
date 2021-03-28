/*
 * Created by Piyush Pradeepkumar on 28/03/21, 11:29 AM.
 *  Copyright (c) 2021 People Interactive. All rights reserved.
 */

package com.thelumiereguy.reactivepostgres.framework.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.thelumiereguy.reactivepostgres.config.AppURLs
import com.thelumiereguy.reactivepostgres.config.AppURLs.notesSubscriptionTopic
import com.thelumiereguy.reactivepostgres.config.AppURLs.stompBrokerEndpoint
import com.thelumiereguy.reactivepostgres.presentation.dto.Note
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.messaging.simp.stomp.StompFrameHandler
import org.springframework.messaging.simp.stomp.StompHeaders
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter
import org.springframework.web.socket.client.standard.StandardWebSocketClient
import org.springframework.web.socket.messaging.WebSocketStompClient
import java.lang.reflect.Type
import java.util.concurrent.BlockingQueue
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.TimeUnit
import org.springframework.messaging.converter.MappingJackson2MessageConverter
import org.springframework.messaging.simp.stomp.StompCommand

import org.springframework.messaging.simp.stomp.StompSession


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
internal class WSNoteControllerTest(
    @Autowired private val objectMapper: ObjectMapper
) {

    @LocalServerPort
    var port: Int = 0

    val WEBSOCKET_URI by lazy {
        "ws://localhost:$port" + AppURLs.wsEndpoint
    }

    lateinit var blockingQueue: BlockingQueue<Note>

    lateinit var stompClient: WebSocketStompClient

    @BeforeEach
    fun setup() {
        blockingQueue = LinkedBlockingDeque()
        stompClient = WebSocketStompClient(StandardWebSocketClient()).apply {
            messageConverter = MappingJackson2MessageConverter()
        }
    }

    @Test
    fun `test`() {
        val session = stompClient
            .connect(WEBSOCKET_URI, object : StompSessionHandlerAdapter() {})[1, TimeUnit.SECONDS]

        session.subscribe(stompBrokerEndpoint + notesSubscriptionTopic, DefaultStompFrameHandler())

        val message = objectMapper.writeValueAsString(Note("", "", "", System.currentTimeMillis(), listOf()))
        session.send(stompBrokerEndpoint + notesSubscriptionTopic, message.toByteArray())

        assertEquals(message, blockingQueue.poll(1, TimeUnit.SECONDS))
    }

    inner class DefaultStompFrameHandler : StompSessionHandlerAdapter() {
        override fun getPayloadType(stompHeaders: StompHeaders): Type {
            return Note::class.java
        }

        override fun handleFrame(stompHeaders: StompHeaders, o: Any?) {
            println(": - $o   ${o!!.javaClass}")
            blockingQueue.offer(o as Note)
        }

        override fun handleException(
            session: StompSession,
            command: StompCommand?,
            headers: StompHeaders,
            payload: ByteArray,
            exception: Throwable
        ) {
            exception.printStackTrace();
        }
    }
}