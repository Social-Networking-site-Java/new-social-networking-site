package com.titus.socialnetworkingsite2.services.ServiceImpl;


import com.titus.socialnetworkingsite2.Dto.Response.MessageContent;
import com.titus.socialnetworkingsite2.Dto.Response.MessageResponse;
import com.titus.socialnetworkingsite2.model.BlackList;
import com.titus.socialnetworkingsite2.repositories.BlackListRepository;
import com.titus.socialnetworkingsite2.repositories.ChatMessageRepository;
import com.titus.socialnetworkingsite2.services.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.titus.socialnetworkingsite2.Dto.Response.ResponseConstants.USER_BLACKLISTED;

@Service
@RequiredArgsConstructor
class chatMessageServiceImpl implements ChatService {

    private final SimpMessagingTemplate messagingTemplate;
    private final BlackListRepository blackListRepository;
    private final ChatMessageRepository chatMessageRepository;


    @Override
    public MessageResponse sendMessage(MessageContent messageContent) {

        // Check if the recipient is blacklisted
        Optional<BlackList> blackList = blackListRepository
                .findByBlacklistedAndBlacklistedBy(messageContent.getRecipient(), messageContent.getSender());

        if (blackList.isPresent()) {
            return new MessageResponse(USER_BLACKLISTED);
        }

        chatMessageRepository.save(messageContent);


        messagingTemplate.convertAndSendToUser(messageContent.getRecipient(), "/topic/chat-message",
                new MessageResponse("Message sent to " + messageContent.getRecipient() + " - " + messageContent.getContent()));



        return new MessageResponse();
    }
}


