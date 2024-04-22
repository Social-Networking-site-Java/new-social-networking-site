package com.titus.socialnetworkingsite2.controllers;

import com.titus.socialnetworkingsite2.model.User;
import com.titus.socialnetworkingsite2.services.UserSearchServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("auth")
public class UserSearchServiceController {

    private final UserSearchServices userSearchServices;

    @PostMapping("/search")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<User>> searchUsers(@RequestParam String searchTeam) {
        List<User> users = userSearchServices.searchUser(searchTeam);
        return ResponseEntity.ok(users);
    }
}
