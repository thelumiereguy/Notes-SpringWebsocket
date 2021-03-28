/*
 * Created by Piyush Pradeepkumar on 28/03/21, 9:46 AM.
 *  Copyright (c) 2021 People Interactive. All rights reserved.
 */

package com.thelumiereguy.reactivepostgres.framework.wsconfig

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.thelumiereguy.reactivepostgres.config.AppURLs
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.config.annotation.*
import org.springframework.web.socket.handler.TextWebSocketHandler

@Configuration
@EnableWebSocketMessageBroker
class WSConfig : WebSocketMessageBrokerConfigurer {

    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        registry.enableSimpleBroker(AppURLs.stompBrokerEndpoint)
        registry.setApplicationDestinationPrefixes(AppURLs.applicationEndpoint)
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.addEndpoint(AppURLs.wsEndpoint).setAllowedOriginPatterns("*")
        registry.addEndpoint(AppURLs.wsEndpoint).setAllowedOriginPatterns("*").withSockJS()
    }

}

//@Configuration
//@EnableWebSocket
//class WSConfigV2 : WebSocketConfigurer {
//    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
//        registry.addHandler(ChatHandler(), "/ws")
//        registry.addHandler(ChatHandler(), "/ws").withSockJS()
//    }
//}
//
//class ChatHandler : TextWebSocketHandler() {
//
//    private val mapper = jacksonObjectMapper()
//
//    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
//        val json = mapper.readTree(message.payload)
//        json?.let {
//            session.sendMessage(TextMessage(mapper.writeValueAsString(it)))
//        }
//    }
//
//
//}