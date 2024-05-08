package com.titus.socialnetworkingsite2.controllers;

import com.titus.socialnetworkingsite2.Exception.GlobalExceptionHandler;
import com.titus.socialnetworkingsite2.model.User;
import com.titus.socialnetworkingsite2.services.UserSearchServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserSearchServiceController {

    private final UserSearchServices userSearchServices;


    @GetMapping("/searchForUsers")
    public ResponseEntity<List<User>> searchUsers(@RequestParam("searchTerm") String searchTeam) {
        List<User> users = userSearchServices.searchUser(searchTeam);
        if (users.isEmpty()) {
            throw new GlobalExceptionHandler.UserAlreadyBlacklistedException("User not found");
        }
        return ResponseEntity.ok(users);
    }


    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> allUser() {
        List<User> users = userSearchServices.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
