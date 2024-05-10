package com.titus.socialnetworkingsite2.services.ServiceImpl;

import com.titus.socialnetworkingsite2.model.ChatMessage;
import com.titus.socialnetworkingsite2.model.User;
import com.titus.socialnetworkingsite2.repositories.ChatMessageRepository;
import com.titus.socialnetworkingsite2.repositories.UserRepository;
import com.titus.socialnetworkingsite2.services.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;


    @Override
    public void saveMessage(ChatMessage chatMessage) {



//
//        // Retrieve the current authenticated user
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String currentUser = auth.getName();
//
//        // Set the current user as the message sender
//        chatMessage.setSender(currentUser);
//
//        // Generate the timestamp when the message is received
//        chatMessage.setTimeStamp(new Date());
//
//        // Save the message to the database
//        chatMessageRepository.save(chatMessage);


        // Retrieve the current authenticated user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = auth.getName();

        // Set the current user as the message sender
        chatMessage.setSender(currentUser);

        // Generate the timestamp when the message is received
        chatMessage.setTimeStamp(new Date());

        // Save the message to the database
        chatMessageRepository.save(chatMessage);

    }
}
