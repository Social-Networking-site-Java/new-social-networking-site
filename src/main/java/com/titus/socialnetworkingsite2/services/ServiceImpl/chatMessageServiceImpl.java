package com.titus.socialnetworkingsite2.services.ServiceImpl;


import com.titus.socialnetworkingsite2.Dto.Response.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class chatMessageService {

    private final SimpMessagingTemplate messagingTemplate;



    public void sendMessageToUser(final String id) {


        MessageResponse response = new MessageResponse();
        response.setMessage(response.getMessage());

        messagingTemplate.convertAndSendToUser(id, "/topic/private-messages", response);

    }


}


