package com.titus.socialnetworkingsite2.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jdk.jfr.Timestamp;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String message_Id;

    @Timestamp
    private Date timeStamp;

    private String sender;
   // private User user;
    private String recipient;
    private String content;

}