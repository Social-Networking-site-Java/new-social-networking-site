package com.titus.socialnetworkingsite2.controllers;


//import com.titus.socialnetworkingsite2.config.websocket.WebsocketUserService;
import com.titus.socialnetworkingsite2.config.websocket.WebsocketUserService;
import com.titus.socialnetworkingsite2.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class WebsocketUserController    {

    private final WebsocketUserService websocketUserService;


    @MessageMapping("/user.addUser")
    @SendTo("/user/topic")
    public User addUser(@Payload User user) {
        websocketUserService.saveUser(user);
        return user;
    }


    @MessageMapping("/user.disconnect")
    public User disconnectUser(@Payload User user) {
        websocketUserService.disconnect(user);
        return user;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(websocketUserService.findConnectedUsers());
    }


}
