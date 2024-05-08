package com.titus.socialnetworkingsite2.controllers;//package com.titus.socialnetworkingsite2.controllers;

import com.titus.socialnetworkingsite2.model.ChatMessage;
import com.titus.socialnetworkingsite2.repositories.ChatMessageRepository;
import com.titus.socialnetworkingsite2.services.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatMessageService chatMessageService;

    @MessageMapping("/hello")
    @PreAuthorize("isAuthenticated()")
    public void chat(@Payload ChatMessage message) throws Exception {

        messagingTemplate.convertAndSendToUser(message.getRecipient(),"/topic/chat", message);


        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("Greeting received");
        System.out.println("message: " + message);
        System.out.println("messagingTemplate: " + messagingTemplate);
        System.out.println(message.getRecipient());
        System.out.println(message.getName());


    }

    @PostMapping("/sendMessage")
    public ResponseEntity<String> ChatMessages(@RequestBody ChatMessage chatMessage){

        chatMessageService.sendMessage(chatMessage);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

}