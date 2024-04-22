package com.titus.socialnetworkingsite2.controllers;

import com.titus.socialnetworkingsite2.Dto.InviteDTO;
import com.titus.socialnetworkingsite2.model.User;
import com.titus.socialnetworkingsite2.services.UserInvitationService;
import com.titus.socialnetworkingsite2.services.UserSearchServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class SendInvitationController {

    private final UserInvitationService userInvitationService;


    @PostMapping("/invite")
    public ResponseEntity<String> sendInvitation(  @RequestBody InviteDTO inviteDTO, Principal connectedUser) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        userInvitationService.createInvite(user,inviteDTO);
       // String inviteLink = "http://localhost:5000/invite/" + InviteDTO.getToken();
      //  String inviteLink = "http://localhost:5000/api/v1/auth/invite?inviteCode=" + InviteDTO.getToken();
        // String inviteLink = generateInviteLink();
        System.out.println("==========================================");
       // System.out.println("invitation link: "+generateInviteLink());
        System.out.println("receiver email: "+inviteDTO.getReceiver());
        System.out.println("Sender email: "+user.getEmail());
        System.out.println("===========================================");
        String inviteResponse = userInvitationService.sendInviteEmail(inviteDTO, connectedUser);
        System.out.println(inviteResponse);

        return new ResponseEntity<>(inviteResponse, HttpStatus.ACCEPTED);

    }

}
