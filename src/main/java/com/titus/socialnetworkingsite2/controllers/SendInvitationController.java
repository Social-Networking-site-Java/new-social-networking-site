package com.titus.socialnetworkingsite2.controllers;

import com.titus.socialnetworkingsite2.Dto.BlackListDTO;
import com.titus.socialnetworkingsite2.Dto.Response.GenResponse;
import com.titus.socialnetworkingsite2.Dto.InviteDTO;
import com.titus.socialnetworkingsite2.model.Invite;
import com.titus.socialnetworkingsite2.services.UserInvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
//@RequestMapping("invitations")
@RequestMapping("api/v1/invitation/")
public class SendInvitationController {

    private final UserInvitationService userInvitationService;


    @PostMapping("/invite")
    public ResponseEntity<GenResponse> sendInvitation(@RequestBody  InviteDTO inviteDTO, BlackListDTO blackListDTO, Principal principal) {
        return new ResponseEntity<>(userInvitationService.createInvite(inviteDTO, blackListDTO, principal), HttpStatus.CREATED);
    }


    @GetMapping("/showAllInvitations")
    public ResponseEntity<List<Invite>> showInvitations() {
        List<Invite> invitations = userInvitationService.getAllInvitations();
        return  ResponseEntity.ok(invitations);
    }


    @GetMapping("/acceptInvite")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public GenResponse acceptInvite(@RequestParam("inviteCode") String inviteCode) {
            userInvitationService.acceptInvite(inviteCode);
            return GenResponse.builder()
                    .status(HttpStatus.ACCEPTED.value())
                    .message("Invite Accepted").build();
    }



    @GetMapping("/declineInvite")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public GenResponse declineInvite(@RequestParam("inviteCode")String inviteCode) {
            userInvitationService.declineInvite(inviteCode);
            return GenResponse.builder()
                    .status(HttpStatus.ACCEPTED.value())
                    .message("Invite Decline").build();
    }

}
