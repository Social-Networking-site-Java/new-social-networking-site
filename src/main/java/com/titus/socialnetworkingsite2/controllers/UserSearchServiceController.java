package com.titus.socialnetworkingsite2.controllers;

import com.titus.socialnetworkingsite2.Exception.GlobalExceptionHandler;
import com.titus.socialnetworkingsite2.model.User;
import com.titus.socialnetworkingsite2.services.UserSearchServices;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.titus.socialnetworkingsite2.Dto.Response.ResponseConstants.USER__NOT__FOUND;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserSearchServiceController {

    private final UserSearchServices userSearchServices;


    @GetMapping("/searchForUsers")
    @Operation(summary = "Search for users")
    public ResponseEntity<List<User>> searchUsers(@RequestParam("users") String searchTeam) {
        List<User> users = userSearchServices.searchUser(searchTeam);
        if (users.isEmpty()) {
            throw new GlobalExceptionHandler.UserAlreadyBlacklistedException(USER__NOT__FOUND);
        }
        return ResponseEntity.ok(users);
    }


    @GetMapping("/getAllUsers")
    @Operation(summary = "List all users")
    public ResponseEntity<List<User>> allUser() {
        List<User> users = userSearchServices.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
