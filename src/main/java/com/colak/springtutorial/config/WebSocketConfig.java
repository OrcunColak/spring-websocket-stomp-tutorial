package com.colak.springtutorial.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
// Enables WebSocket message handling, backed by a message broker.
@EnableWebSocketMessageBroker
@Slf4j
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {

            // Invoked before the Message is actually sent to the channel.
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                // Authentication based on username and password in message header
                MessageHeaders headers = message.getHeaders();
                log.info("headers: {}", headers);
                return message;
            }

            private boolean isValidUser(String username, String password) {
                return true;
            }
        });
    }

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
        // For example "new StompJs.Client({brokerURL: 'ws://localhost:8080/gs-guide-websocket'});"
        registry.addEndpoint("/gs-guide-websocket");
        //.setAllowedOrigins("http://localhost:8080");
        // This does not work
        // .withSockJS();
    }
}
