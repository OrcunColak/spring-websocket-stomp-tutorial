package com.colak.springwebsockettutorial.controller.greeting;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;
import java.util.concurrent.CountDownLatch;

@Slf4j
@RequiredArgsConstructor
@Getter
public class MyStompSessionHandler extends StompSessionHandlerAdapter {

    private final CountDownLatch countDownLatch;

    private String content;

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return GreetingMessage.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        GreetingMessage greetingMessage = (GreetingMessage) payload;
        content = greetingMessage.content();
        log.info("Received : {}", content);
        countDownLatch.countDown();
    }
}
