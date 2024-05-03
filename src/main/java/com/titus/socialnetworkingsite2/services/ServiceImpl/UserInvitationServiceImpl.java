package com.titus.socialnetworkingsite2.services.ServiceImpl;

import com.titus.socialnetworkingsite2.Dto.BlackListDTO;
import com.titus.socialnetworkingsite2.Dto.Response.GenResponse;
import com.titus.socialnetworkingsite2.Dto.InviteDTO;
import com.titus.socialnetworkingsite2.model.Invite;
import com.titus.socialnetworkingsite2.repositories.BlackListRepository;
import com.titus.socialnetworkingsite2.repositories.InviteRepository;
import com.titus.socialnetworkingsite2.repositories.UserRepository;
import com.titus.socialnetworkingsite2.services.UserInvitationService;
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

@Service
@RequiredArgsConstructor
public class UserInvitationServiceImpl implements UserInvitationService {

    private final JavaMailSender mailSender;
    private final InviteRepository inviteRepository;
    private final BlackListRepository blackListRepository;
    private final UserRepository userRepository;

    // Generate a stable token at the class level
    private static final String INVITATION_TOKEN = generateToken();


    public GenResponse createInvite(InviteDTO inviteDTO, BlackListDTO blackListDTO, Principal principal) {


       Invite invite = inviteRepository.findBySender(inviteDTO.getSender());

       if (invite == null) {
           return GenResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message(" User Invite Sent").build();

       }


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) auth.getPrincipal();


        Invite newInvite = Invite.builder()
                .recipientEmail(inviteDTO.getRecipientEmail())
                .accepted(false)
                .sender(user.getUsername())
                .inviteCode(INVITATION_TOKEN)
                .build();

        inviteRepository.save(newInvite);


        SimpleMailMessage message = getSimpleMailMessage(inviteDTO, newInvite, principal);
        mailSender.send(message);

        return GenResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("Invite sent successfully to " + inviteDTO.getRecipientEmail()).build();



    }




    private static SimpleMailMessage getSimpleMailMessage(InviteDTO inviteDTO, Invite newInvite, Principal principal) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("iakwasititus@gmail.com");
        message.setTo(inviteDTO.getRecipientEmail());
        message.setSubject("Invitation to Connect with " + principal.getName().toLowerCase());
        message.setText("Hi... \n please click this link to accept my invite " + generateInviteLink(newInvite) );
        return message;
    }




    // Method to generate a stable token
    private static String generateToken() {
        return UUID.randomUUID().toString();
    }


    public static String generateInviteLink(Invite newInvite) {
        return "http://localhost:5000/api/v1/invitation/acceptInvite?inviteCode=" + newInvite.getInviteCode();
    }


    public List<String> getAllInvitations() {
        return inviteRepository.findAllRecipientEmails();
    }

    @Override
    public void acceptInvite(String inviteCode) {

        // getting the user by the invitation code
        Optional<Invite> inviteOptional = inviteRepository.findByInviteCode(inviteCode);


        if (inviteOptional.isPresent()) {
            Invite invite = inviteOptional.get();
            invite.setAccepted(true);

            inviteRepository.save(invite);
        }

        GenResponse.builder()
                .status(HttpStatus.ACCEPTED.value())
                .message("Invite Accepted").build();

    }

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
}









