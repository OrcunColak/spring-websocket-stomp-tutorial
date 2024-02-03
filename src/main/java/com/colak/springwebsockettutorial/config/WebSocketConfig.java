package com.colak.springwebsockettutorial.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
// Enables WebSocket message handling, backed by a message broker.
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Enable a simple memory-based message broker
        // Allows clients to subscribe to and receive messages from the specified destination prefixed with /topic
        // For example stompClient.subscribe('/topic/greetings',...)
        // So messages sent to topics starting with /topic will be broadcast to connected clients
        config.enableSimpleBroker("/topic");

        // Clients can send messages to destinations that start with this prefix.
        // For example, a client can send a message to /app/hello.
        // Method needs to be annotated with @MessageMapping("/hello")
        // For example stompClient.publish({destination: "/app/hello", body: JSON.stringify(...)});
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Register a Stomp (Simple Text Oriented Messaging Protocol) endpoint that clients can connect to.
        // For example new StompJs.Client({brokerURL: 'ws://localhost:8080/gs-guide-websocket'});
        registry.addEndpoint("/gs-guide-websocket");
                //.setAllowedOrigins("http://localhost:8080");
        // This does not work
        // .withSockJS();
    }
}
