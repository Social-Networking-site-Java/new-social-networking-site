package com.titus.socialnetworkingsite2.controllers;//package com.titus.socialnetworkingsite2.controllers;
//
//import com.titus.socialnetworkingsite2.model.HelloMessage;
//import lombok.RequiredArgsConstructor;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Controller;
//
//@Controller
//@RequiredArgsConstructor
//public class GreetingController {
//
//    private final SimpMessagingTemplate messagingTemplate;
//
//    @MessageMapping("/hello")
//    public String greeting(@Payload HelloMessage message) throws Exception {
//
//        messagingTemplate.convertAndSendToUser(message.getRecipient(),"/topic/greetings", message);
//
//        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++");
//        System.out.println("Greeting received");
//        System.out.println("message: " + message);
//        System.out.println("messagingTemplate: " + messagingTemplate);
//        System.out.println(message.getRecipient());
//        System.out.println(message.getName());
//
//return "Greeting received";
//
//    }
//
//}

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
        String recipient = message.getRecipient();
        String sender = message.getName();
        String content = message.getContent();

        // Send the message back to the sender
        messagingTemplate.convertAndSendToUser(sender, "/topic/greetings", "Message sent to " + recipient + ": " + content);

        // Optionally, you can also send an acknowledgment message to the sender
        messagingTemplate.convertAndSendToUser(sender, "/topic/greetings", "Your message has been sent to " + recipient);

        System.out.println("Greeting received from " + sender + " to " + recipient + ": " + content);
    }
}
