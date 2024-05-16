package com.titus.socialnetworkingsite2.controllers;

import com.titus.socialnetworkingsite2.Dto.Response.MessageContent;
import com.titus.socialnetworkingsite2.Dto.Response.MessageResponse;
import com.titus.socialnetworkingsite2.services.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor

public class ChatController2 {

    private final ChatService chatService;


    @MessageMapping("/chat-message")
    @SendToUser("/topic/chat-message")
    public ResponseEntity<MessageResponse> sendMessage(@Payload MessageContent messageContent) throws InterruptedException {
        Thread.sleep(1000);
        return new ResponseEntity<>(chatService.sendMessage(messageContent), HttpStatus.OK);
    }

}
