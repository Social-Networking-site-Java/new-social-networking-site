package com.titus.socialnetworkingsite2.controllers;

import com.titus.socialnetworkingsite2.Dto.InviteDTO;
import com.titus.socialnetworkingsite2.model.Invite;
import com.titus.socialnetworkingsite2.model.User;
import com.titus.socialnetworkingsite2.repositories.UserRepository;
import com.titus.socialnetworkingsite2.services.UserInvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class SendInvitationController {

    private final UserInvitationService userInvitationService;
    private final UserRepository userRepository;



    @PostMapping("/invite")
    public ResponseEntity<String> sendInvitation(  @RequestBody InviteDTO inviteDTO, Principal connectedUser, String inviteToken) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        userInvitationService.createInvite(user,inviteDTO, inviteToken);
        String inviteResponse = userInvitationService.sendInviteEmail(inviteDTO, connectedUser);
        return new ResponseEntity<>(inviteResponse, HttpStatus.ACCEPTED);

    }



    @GetMapping("/showAllInvitations")
    public ResponseEntity<List<Invite>> showInvitations() {

       // User userEmail = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
      // String userEmail = connectedUser.getName();
//        User userEmail = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//       User user = userRepository.findByEmail(userEmail.getEmail())
//               .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        // Fetch invitations for the user
        List<Invite> invitations = userInvitationService.getInvitationsForUser();

        return  ResponseEntity.ok(invitations);

    }


    @PostMapping("/acceptInvite")
    public ResponseEntity<String> acceptInvite(@RequestParam("inviteCode") String inviteCode) {
        try {
            userInvitationService.acceptInvite(inviteCode);
            return ResponseEntity.accepted().body("Accepted invitation");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/declineInvite")
    public ResponseEntity<String> declineInvite(@RequestParam("inviteCode") String inviteCode) {
        try {
            userInvitationService.declineInvite(inviteCode);
            return ResponseEntity.accepted().body("Declined Invitation");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
