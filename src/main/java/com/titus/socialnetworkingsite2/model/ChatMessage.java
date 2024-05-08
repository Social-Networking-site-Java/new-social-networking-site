package com.titus.socialnetworkingsite2.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatMessage {

    private String name;
    private String recipient;
    private String content;

}