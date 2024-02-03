package com.colak.springwebsockettutorial.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {


    // http://localhost:8080

    /**
     * The payload of the message is bound to a HelloMessage object
     * The method creates a GreetingMessage object and returns it.
     * The return value is broadcast to all subscribers of /topic/greetings, as specified in the @SendTo annotation.
     */

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public GreetingMessage greeting(HelloMessage message) throws Exception {
        // Note that the name from the input message is sanitized, since, in this case, it will be echoed back
        // and re-rendered in the browser DOM on the client side.
        return new GreetingMessage(HtmlUtils.htmlEscape(message.name()) + "!");
    }

}
