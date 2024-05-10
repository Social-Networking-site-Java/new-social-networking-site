package com.titus.socialnetworkingsite2.controllers;

import com.titus.socialnetworkingsite2.Dto.BlackListDTO;
import com.titus.socialnetworkingsite2.Dto.Response.GenResponse;
import com.titus.socialnetworkingsite2.Dto.InviteDTO;
import com.titus.socialnetworkingsite2.model.Invite;
import com.titus.socialnetworkingsite2.services.UserInvitationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/invitation/")
public class InvitationController {

    private final UserInvitationService userInvitationService;

    @PostMapping("/invite")
    @Operation(summary = "First time inviting users")
    public ResponseEntity<GenResponse> sendInvitation(
            @RequestBody  InviteDTO inviteDTO) {
        return new ResponseEntity<>(
                userInvitationService.createInvite(inviteDTO), HttpStatus.CREATED);
    }

    @GetMapping("/acceptInvite/{inviteCode}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Pass invite code to accept Invite")
    public GenResponse acceptInvite(@PathVariable("inviteCode") String inviteCode) {
            userInvitationService.acceptInvite(inviteCode);
            return GenResponse.builder()
                    .status(HttpStatus.ACCEPTED.value())
                    .message("Invite Accepted").build();
    }


    @DeleteMapping("/declineInvite/{inviteCode}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Pass invite code to Delcine Invite")
    public GenResponse declineInvite(@PathVariable("inviteCode")String inviteCode) {
            userInvitationService.declineInvite(inviteCode);
            return GenResponse.builder()
                    .status(HttpStatus.ACCEPTED.value())
                    .message("Invite Decline").build();
    }


    // Get All received Invitation
    @GetMapping("/getAllReceivedInvites/{sender}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "All Received Invites")
    public List<Invite> getAllReceivedInvites(@PathVariable("sender") String sender){
        return userInvitationService.getAllReceivedInvitation(sender);
    }


    @GetMapping("/getAllAcceptedInvitations/{sender}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all accepted invites")
    public ResponseEntity<List<Invite>> showAllAcceptedInvitations(@PathVariable("sender") String sender) {
        return  ResponseEntity.ok(userInvitationService.getAllAcceptedInvitations(sender));
    }

    @GetMapping("getAllInvitations")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all invitations")
    public ResponseEntity<List<Invite>> getAllInvitations() {
        return ResponseEntity.ok(userInvitationService.getAllInvitations());
    }

}
