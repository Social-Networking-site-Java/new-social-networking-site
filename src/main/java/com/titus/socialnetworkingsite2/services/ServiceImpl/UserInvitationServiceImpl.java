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

//        if (inviteRepository.findBySender(inviteDTO.getSender())) {
//            return GenResponse.builder()
//                    .status(HttpStatus.OK.value())
//                    .message("Invite already sent 222").build();
//        }

       Invite invite = inviteRepository.findBySender(inviteDTO.getSender());

       if (invite == null) {
           return GenResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message(" User Invite Sent").build();

       }

//        if (inviteRepository.existsByRecipientEmail(inviteDTO.getRecipientEmail())) {
//            return GenResponse.builder()
//                    .status(HttpStatus.OK.value())
//                    .message("Invite already sent").build();
//        }




        // Check if blackListUser is already in the blacklist
//        if (blackListRepository.findByBlacklistedAndBlacklistedBy(blackListDTO.getBlacklisted(), blackListDTO.getBlacklistedBy())){
//            return GenResponse.builder()
//                    .status(HttpStatus.OK.value())
//                    .message("User is blacklisted, can't connect ").build();
//        }

//      var existingUser =  userRepository.findByEmail(inviteDTO.getRecipientEmail());
//        if (existingUser.isEmpty()) {
//            return GenResponse.builder()
//                    .status(HttpStatus.OK.value())
//                    .message("No user").build();
//        }


//        BlackList blackListedUser = blackListRepository.findByBlacklistedAndBlacklistedBy(blackListDTO.getBlacklisted(), blackListDTO.getBlacklistedBy());
//        if (Objects.equals(blackListedUser.getBlacklisted(), blackListDTO.getBlacklistedBy())) {
//            return GenResponse.builder()
//                    .status(HttpStatus.OK.value())
//                    .message("User is blacklisted, can't connect ").build();
//        }






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


//    @Override
//    public String sendInviteEmail(InviteDTO receiver, Principal connectedUser, String email) {
//        MimeMessage message = mailSender.createMimeMessage();
//
//        // getting the current logged user
//        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
//
//        //user invitation email draft
//        try {
//            message.setSubject("Invitation from " + user.fullName());
//            message.setFrom("iakwasititus@gmail.com");
//            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiver.getRecipientEmail()));
//            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(email));
//
//            // Build email content
//            String content = "Hi there,\n\n" +
//                    "I invite you to connect with me\n" +
//                    "Please Click the link below to accept my invitation\n\n" +
//                    generateInviteLink() + "\n\n" +
//                    "Thanks,\n" +
//                    user.getFirstname();
//            message.setContent(content, "text/plain");
//
//            mailSender.send(message);
//            System.out.println("Invite email sent successfully to " + email +" from " + user.fullName());
//        } catch (MessagingException e) {
//            System.err.println("Error sending invite email: " + e.getMessage());
//        }
//
//        return "Invitation sent successfully";
//    }




    // Method to generate a stable token
    private static String generateToken() {
        return UUID.randomUUID().toString();
    }



    public static String generateInviteLink(Invite newInvite) {
        return "http://localhost:5000/api/v1/invitation/acceptInvite?inviteCode=" + newInvite.getInviteCode();
    }


    @Override
    public List<Invite> getAllInvitations() {
        return inviteRepository.findAll();
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









