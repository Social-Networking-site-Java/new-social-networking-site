package com.titus.socialnetworkingsite2.controllers;//package com.titus.socialnetworkingsite2.controllers;

import com.titus.socialnetworkingsite2.Dto.Response.GenResponse;
import com.titus.socialnetworkingsite2.model.BlackList;
import com.titus.socialnetworkingsite2.model.ChatMessage;
import com.titus.socialnetworkingsite2.repositories.BlackListRepository;
import com.titus.socialnetworkingsite2.services.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

import static com.titus.socialnetworkingsite2.Dto.Response.ResponseConstants.CHAT_MESSAGE_SENT_SUCCESSFULLY;
import static com.titus.socialnetworkingsite2.Dto.Response.ResponseConstants.USER_BLACKLISTED;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat")
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;

    private final BlackListRepository blackListRepository;

    @MessageMapping("/sendChat")
    public GenResponse chat(@Payload ChatMessage message) {

        // Retrieve the recipient username from the message
        String recipient = message.getRecipient();


        // Check if the recipient is blacklisted
        Optional<BlackList> blackList = blackListRepository.findByBlacklistedAndBlacklistedBy(recipient, message.getSender());

        if (blackList.isPresent()) {
            return GenResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message(USER_BLACKLISTED)
                    .build();
        }

        // Save the incoming chat message using the ChatService
        chatMessageService.saveMessage(message);

        // Send the message to the recipient's WebSocket topic
        messagingTemplate.convertAndSendToUser(recipient, "/topic/chat", message);

        // Return success response
        return GenResponse.builder()
                .status(HttpStatus.OK.value())
                .message(CHAT_MESSAGE_SENT_SUCCESSFULLY)
                .build();
    }


















//    @PostMapping("/saveMessage")
//    public ResponseEntity<String> SaveMessages(@RequestBody ChatMessage chatMessage){
//
//        chatMessageService.saveMessage(chatMessage);
//        return ResponseEntity.status(HttpStatus.CREATED).body("Message save successfully");
//    }

}

