package com.titus.socialnetworkingsite2.config.websocket.chatRoom;


import jakarta.persistence.Id;
import lombok.*;

import java.lang.annotation.Documented;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
// add document anotation
public class ChatRoom {

    @Id
    private String id;
    private String chatId;
    private String sender;
    private String receiverId;
}
