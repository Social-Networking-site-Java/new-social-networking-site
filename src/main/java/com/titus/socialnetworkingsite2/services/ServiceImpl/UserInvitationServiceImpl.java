package com.titus.socialnetworkingsite2.services.ServiceImpl;

import com.titus.socialnetworkingsite2.Dto.Response.GenResponse;
import com.titus.socialnetworkingsite2.Dto.InviteDTO;
import com.titus.socialnetworkingsite2.model.Invite;
import com.titus.socialnetworkingsite2.repositories.InviteRepository;
import com.titus.socialnetworkingsite2.repositories.UserRepository;
import com.titus.socialnetworkingsite2.services.UserInvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.titus.socialnetworkingsite2.Dto.Response.ResponseConstants.*;

@Service
@RequiredArgsConstructor
public class UserInvitationServiceImpl implements UserInvitationService {

    private final InviteRepository inviteRepository;
    private final UserRepository userRepository;


    // Method to generate a stable token
    private static String generateToken() {
        return UUID.randomUUID().toString();
    }


    public GenResponse createInvite(InviteDTO inviteDTO) {

        String invitationToken = generateToken();


        //Current user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) auth.getPrincipal();




        // check if user exist
        var existingUser = userRepository.findByUsernameIgnoreCase(inviteDTO.getRecipient().trim());
        if (existingUser.isEmpty()) {
            return GenResponse.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message(inviteDTO.getRecipient() + USER_NOT_FOUND).build();

        }



        // Check if user is already invited
        var existingInviteUser = inviteRepository.findByRecipientAndSenderIgnoreCase(existingUser.get().getUsername().trim(),user.getUsername());
        if (existingInviteUser.isPresent()) {
            return GenResponse.builder()
                    .status(HttpStatus.CREATED.value())
                    .message(INVITE_SENT_ALREADY + inviteDTO.getRecipient()).build();
        }


        // build invite
        Invite newInvite = Invite.builder()
                .recipient(existingUser.get().getUsername())
                .accepted(false)
                .sender(user.getUsername())
                .inviteCode(invitationToken)
                .build();

        // save invite
        inviteRepository.save(newInvite);


        // Invite response
        return GenResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message(INVITE_SENT_SUCCESSFULLY + inviteDTO.getRecipient()).build();
    }





    // accept invite
    @Override
    public GenResponse acceptInvite(String inviteCode) {

        // getting the user by the invitation code
        Optional<Invite> inviteOptional = inviteRepository.findByInviteCode(inviteCode);


        // setting is accepted to true to accept the invite
        if (inviteOptional.isPresent()) {
            Invite invite = inviteOptional.get();

            invite.setAccepted(true);

            inviteRepository.save(invite);
        }


         return GenResponse.builder()
                .status(HttpStatus.ACCEPTED.value())
                .message(INVITE_ACCEPTED_SUCCESSFULLY)
                .build();
    }

    // reject invite
    @Override
    public GenResponse declineInvite(String inviteCode) {

        Optional<Invite> inviteOptional = inviteRepository.findByInviteCode(inviteCode);

        if (inviteOptional.isPresent()) {
            Invite invite = inviteOptional.get();

            inviteRepository.delete(invite);
        }
        return    GenResponse.builder()
                .status(HttpStatus.ACCEPTED.value())
                .message(INVITE_DECLINE_SUCCESSFULLY).build();
    }


    // get all accepted invite
    public List<Invite> getAllAcceptedInvitations(String sender) {
        return inviteRepository.findByAcceptedTrueAndSender(sender);
    }

    @Override
    public List<Invite> getAllReceivedInvitation(String sender) {
        return inviteRepository.findAllBySender(sender);
    }

    @Override
    public List<Invite> getAllInvitations() {
        return inviteRepository.findAll();
    }
}









