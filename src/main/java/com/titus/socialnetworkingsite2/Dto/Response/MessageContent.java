package com.titus.socialnetworkingsite2.Dto.Response;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class MessageContent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String message_Id;


    private String sender;
    private String recipient;
    private String content;

}

