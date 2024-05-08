package com.titus.socialnetworkingsite2.services;

import com.titus.socialnetworkingsite2.model.ChatMessage;
import org.springframework.stereotype.Service;

@Service
public interface ChatMessageService {
    void sendMessage(ChatMessage chatMessage);
}
