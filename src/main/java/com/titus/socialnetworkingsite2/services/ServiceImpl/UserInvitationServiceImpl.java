package com.titus.socialnetworkingsite2.services.ServiceImpl;

import com.titus.socialnetworkingsite2.Dto.BlackListDTO;
import com.titus.socialnetworkingsite2.Dto.Response.GenResponse;
import com.titus.socialnetworkingsite2.Dto.InviteDTO;
import com.titus.socialnetworkingsite2.model.Invite;
import com.titus.socialnetworkingsite2.repositories.InviteRepository;
import com.titus.socialnetworkingsite2.repositories.UserRepository;
import com.titus.socialnetworkingsite2.services.UserInvitationService;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.titus.socialnetworkingsite2.Dto.Response.ResponseConstants.*;

@Service
@RequiredArgsConstructor
public class UserInvitationServiceImpl implements UserInvitationService {

    private final JavaMailSender mailSender;
    private final InviteRepository inviteRepository;
    private final UserRepository userRepository;


    // Generate a stable token at the class level
    private static final String INVITATION_TOKEN = generateToken();


    public GenResponse createInvite(InviteDTO inviteDTO) {

        //Current user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) auth.getPrincipal();

        // check if user exist
        var existingUser = userRepository.findByUsername(inviteDTO.getRecipientEmail().trim());
        if (existingUser.isEmpty()) {
            return GenResponse.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message(inviteDTO.getRecipientEmail() +   USER_NOT_FOUND).build();
        }

     var existingInviteUser = inviteRepository.findByRecipientEmailAndSender(inviteDTO.getRecipientEmail().trim(),user.getUsername());
        if (existingInviteUser.isPresent()) {
            return GenResponse.builder()
                    .status(HttpStatus.CREATED.value())
                    .message(INVITE_SENT_ALREADY + inviteDTO.getRecipientEmail()).build();
        }


        // build invite
        Invite newInvite = Invite.builder()
                .recipientEmail(inviteDTO.getRecipientEmail())
                .accepted(false)
                .sender(user.getUsername())
                .inviteCode(INVITATION_TOKEN)
                .build();

        // save invite
        inviteRepository.save(newInvite);


//        // send invite email [optional]
//        SimpleMailMessage message = getSimpleMailMessage(inviteDTO, newInvite, principal);
//        mailSender.send(message);

        // Invite response
        return GenResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message(INVITE_SENT_SUCCESSFULLY + inviteDTO.getRecipientEmail()).build();
    }


    // invite email message
    private static SimpleMailMessage getSimpleMailMessage(InviteDTO inviteDTO, Invite newInvite, Principal principal) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("iakwasititus@gmail.com");
        message.setTo(inviteDTO.getRecipientEmail());
        message.setSubject("Invitation to Connect with " + principal.getName().toLowerCase());
        message.setText("Hi... \nPlease click this link to accept my invite " + generateInviteLink(newInvite) );
        return message;
    }

    // Method to generate a stable token
    private static String generateToken() {
        return UUID.randomUUID().toString();
    }


    // invite email link
    public static String generateInviteLink(Invite newInvite) {
        return "http://localhost:5000/api/v1/invitation/acceptInvite?inviteCode=" + newInvite.getInviteCode();
    }


    // accept invite
    @Override
    public void acceptInvite(String inviteCode) {

        // getting the user by the invitation code
        Optional<Invite> inviteOptional = inviteRepository.findByInviteCode(inviteCode);

        // setting is accepted to true to accept the invite
        if (inviteOptional.isPresent()) {
            Invite invite = inviteOptional.get();
            invite.setAccepted(true);

            inviteRepository.save(invite);
        }

        // response
        GenResponse.builder()
                .status(HttpStatus.ACCEPTED.value())
                .message("Invite Accepted").build();
    }

    // reject invite
    @Override
    public void declineInvite(String inviteCode) {

        Optional<Invite> inviteOptional = inviteRepository.findByInviteCode(inviteCode);

        if (inviteOptional.isPresent()) {
            Invite invite = inviteOptional.get();
            invite.setAccepted(false);

            inviteRepository.delete(invite);
        }
        GenResponse.builder()
                .status(HttpStatus.ACCEPTED.value())
                .message("Invite Decline").build();
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









