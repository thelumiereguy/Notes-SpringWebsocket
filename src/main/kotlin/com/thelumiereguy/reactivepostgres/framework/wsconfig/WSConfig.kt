/*
 * Created by Piyush Pradeepkumar on 28/03/21, 9:46 AM.
 *  Copyright (c) 2021 People Interactive. All rights reserved.
 */

package com.thelumiereguy.reactivepostgres.framework.wsconfig

import com.thelumiereguy.reactivepostgres.config.AppURLs
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

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