package com.titus.socialnetworkingsite2.controllers;

import com.titus.socialnetworkingsite2.Dto.Response.GenResponse;
import com.titus.socialnetworkingsite2.Dto.InviteDTO;
import com.titus.socialnetworkingsite2.model.Invite;
import com.titus.socialnetworkingsite2.services.UserInvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("invitations")
public class SendInvitationController {

    private final UserInvitationService userInvitationService;


    @PostMapping("/invite")
    public ResponseEntity<GenResponse> sendInvitation(@RequestBody  InviteDTO inviteDTO) {
        return new ResponseEntity<>(userInvitationService.createInvite(inviteDTO), HttpStatus.CREATED);
    }


    @GetMapping("/showAllInvitations")
    public ResponseEntity<List<Invite>> showInvitations() {
        List<Invite> invitations = userInvitationService.getAllInvitations();
        return  ResponseEntity.ok(invitations);
    }


    @GetMapping("/acceptInvite")
    public GenResponse acceptInvite(@RequestParam("inviteCode") String inviteCode) {
            userInvitationService.acceptInvite(inviteCode);
            return GenResponse.builder()
                    .status(HttpStatus.ACCEPTED.value())
                    .message("Invite Accepted").build();
    }



    @GetMapping("/declineInvite")
    public GenResponse declineInvite(@RequestParam("inviteCode")String inviteCode) {
            userInvitationService.declineInvite(inviteCode);
            return GenResponse.builder()
                    .status(HttpStatus.ACCEPTED.value())
                    .message("Invite Decline").build();
    }

}
