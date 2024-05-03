package com.titus.socialnetworkingsite2.controllers;

import com.titus.socialnetworkingsite2.model.HelloMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class GreetingController {

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/hello")
    public void greeting(@Payload HelloMessage message) throws Exception {

        messagingTemplate.convertAndSendToUser(message.getRecipient(),"/topic/greetings", message);

    }

}