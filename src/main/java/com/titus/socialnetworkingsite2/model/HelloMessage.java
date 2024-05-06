package com.titus.socialnetworkingsite2.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class HelloMessage {

    private String name;
    private String recipient;
    private String content;

    public HelloMessage() {
    }

    public HelloMessage(String name) {
        this.name = name;
    }

}