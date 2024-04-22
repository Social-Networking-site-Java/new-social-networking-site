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

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class SendInvitation {

    private final UserInvitationService userInvitationService;


    @PostMapping("/invite")
    public ResponseEntity<String> sendInvitation(@RequestBody InviteDTO inviteDTO) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        userInvitationService.createInvite(user.getEmail(),inviteDTO);
        String inviteLink = "http://localhost:5000/invite/" + InviteDTO.getToken();
        System.out.println("==========================================");
        System.out.println(inviteLink);
        System.out.println( InviteDTO.getToken());
        System.out.println(inviteDTO.getReceiver());
        System.out.println(user.getEmail());
        System.out.println("===========================================");
        String inviteResponse = userInvitationService.sendInviteEmail(inviteDTO, inviteLink);
        System.out.println(inviteResponse);

        return new ResponseEntity<>(inviteResponse, HttpStatus.ACCEPTED);

    }

}
