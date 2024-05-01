package com.titus.socialnetworkingsite2.controllers;

import com.titus.socialnetworkingsite2.model.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public String sendMessage(Message message) {


        // Get the authenticated user's information
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();

      //  messagingTemplate.convertAndSend("/topic/messages/" + message.getReceiver(), message);

        // Set the sender of the message as the authenticated user's username
      //  message.setSender(username);



        return HtmlUtils.htmlEscape(message.getContent());
    }
}