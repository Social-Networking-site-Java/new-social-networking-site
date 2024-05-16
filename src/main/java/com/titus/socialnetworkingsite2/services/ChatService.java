package com.titus.socialnetworkingsite2.services;


import com.titus.socialnetworkingsite2.Dto.Response.MessageContent;
import com.titus.socialnetworkingsite2.Dto.Response.MessageResponse;
import org.springframework.stereotype.Service;

@Service
public interface ChatService {
    MessageResponse sendMessage(MessageContent messageContent);
}
