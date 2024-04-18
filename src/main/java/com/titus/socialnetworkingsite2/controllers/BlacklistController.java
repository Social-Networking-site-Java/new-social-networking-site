package com.titus.socialnetworkingsite2.controllers;

import com.titus.socialnetworkingsite2.Dto.BlackListUsers;
import com.titus.socialnetworkingsite2.model.User;
import com.titus.socialnetworkingsite2.repositories.BlacklistRepository;
import com.titus.socialnetworkingsite2.repositories.UserRepository;
import com.titus.socialnetworkingsite2.services.BlackListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class BlacklistController {

    private final BlackListService blackListService;
    private final UserRepository userRepository;
    private final BlacklistRepository blacklistRepository;



    // Endpoint for adding a user to the blacklist
    @PostMapping("/add")
    public ResponseEntity<?> addToBlacklist(@RequestBody BlackListUsers user) {

        // Add the existing user to the blacklist
        blackListService.addToBlacklist(user);
        return ResponseEntity.ok().build();
    }




   // Endpoint for removing a user from the blacklist
    //....

    // Endpoint for checking if a user is blacklisted
    //....



}
