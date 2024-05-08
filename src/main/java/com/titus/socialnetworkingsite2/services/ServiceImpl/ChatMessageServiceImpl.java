package com.titus.socialnetworkingsite2.services.ServiceImpl;

import com.titus.socialnetworkingsite2.model.ChatMessage;
import com.titus.socialnetworkingsite2.repositories.ChatMessageRepository;
import com.titus.socialnetworkingsite2.services.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    @Override
    public void sendMessage(ChatMessage chatMessage) {
        chatMessageRepository.save(chatMessage);


    }
}
