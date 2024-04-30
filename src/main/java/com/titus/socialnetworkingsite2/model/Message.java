package com.titus.socialnetworkingsite2.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Message {
    private String content;
    private String sender;
    private String receiver;
}
