package com.colak.springwebsockettutorial.controller.greeting;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class GreetingControllerTest {

    @Test
    void testGreeting() throws ExecutionException, InterruptedException {

        WebSocketClient client = new StandardWebSocketClient();

        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());


        CountDownLatch countDownLatch = new CountDownLatch(1);
        MyStompSessionHandler sessionHandler = new MyStompSessionHandler(countDownLatch);

        String url = "ws://localhost:8080/gs-guide-websocket";
        StompSession stompSession = stompClient.connectAsync(url, sessionHandler).get();
        stompSession.subscribe("/topic/greetings", sessionHandler);
        String name = "new user";
        stompSession.send("/app/hello", new HelloMessage(name));
        countDownLatch.await();
        stompSession.disconnect();

        assertEquals("Hello new user!", sessionHandler.getContent());

    }
}
