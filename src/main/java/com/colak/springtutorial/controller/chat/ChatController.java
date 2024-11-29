package com.colak.springtutorial.controller.chat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class ChatController {

    private final Map<String, List<UserText>> chats = new HashMap<>();

    /**
     * Using the annotation @DestinationVariable, we can obtain the chat ID.
     */
    @MessageMapping("/chat/{chatId}")
    @SendTo("/topic/chat/{chatId}")
    public List<UserText> sendMessageWithWebsocket(@DestinationVariable String chatId,
                                                   @Payload Message<UserText> message) {
        log.info("new message arrived in chat with id {}", chatId);
        // Store the messages in an array
        List<UserText> messages = chats.getOrDefault(chatId, new ArrayList<>());
        // Store that array in a map with the chat ID as its identifier
        UserText userText = message.getPayload();
        messages.add(userText);

        chats.put(chatId, messages);
        return messages;
    }
}
