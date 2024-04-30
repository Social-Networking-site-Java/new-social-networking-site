package com.titus.socialnetworkingsite2.services.ServiceImpl;

import com.titus.socialnetworkingsite2.Dto.Response.GenResponse;
import com.titus.socialnetworkingsite2.Dto.InviteDTO;
import com.titus.socialnetworkingsite2.model.Invite;
import com.titus.socialnetworkingsite2.model.User;
import com.titus.socialnetworkingsite2.repositories.ContactRepository;
import com.titus.socialnetworkingsite2.repositories.InviteRepository;
import com.titus.socialnetworkingsite2.repositories.UserRepository;
import com.titus.socialnetworkingsite2.services.UserInvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserInvitationServiceImpl implements UserInvitationService {

    private final JavaMailSender mailSender;
    private final UserRepository userRepository;
    private final InviteRepository inviteRepository;
    private final ContactRepository contactRepository;


    // Generate a stable token at the class level
    private static final String INVITATION_TOKEN = generateToken();


    @Override
    public GenResponse createInvite(InviteDTO inviteDTO) {

//        // check if user exit in already
//        var  existingInvite = userRepository.findByEmail(inviteDTO.getReceiver());
//      //  var  existingInvite = inviteRepository.findByRecipientEmail(inviteDTO.getReceiver());
//        if (existingInvite.isPresent()) {
//            throw new RuntimeException("Invite already sent to this email");
//        }

        // Get user from invite repository
      //  Optional<Invite> inviteOptional = inviteRepository.findByInviteCode(inviteDTO.getInviteCode());

        // set user to true by setting isAccepted to true
//        if (inviteOptional.isPresent()) {
//            Invite invite = inviteOptional.get();
//            invite.setAccepted(true);
//            inviteRepository.save(invite);
//        }

//        Optional<InviteDTO> res =  inviteRepository.findByRecipientEmail(inviteDTO.getRecipientEmail());
//       if (res.isPresent()) {
//           throw new RuntimeException("Invite already sent to this email");
//       }

       // System.out.println(inviteRepository.findByRecipientEmail(inviteDTO.getRecipientEmail()));

        if (inviteRepository.existsByRecipientEmail(inviteDTO.getRecipientEmail())){
            return GenResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message("Invite already sent!").build();
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Invite newInvite = Invite.builder()
                .recipientEmail(inviteDTO.getRecipientEmail())
                .accepted(false)
                //.sender(inviteDTO.getSender())
                .sender(String.valueOf(user.getId()))
                .inviteCode(INVITATION_TOKEN)
                .build();


        inviteRepository.save(newInvite);
        //return "Invitation sent to " + inviteDTO.getRecipientEmail();
        return GenResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("Invite sent successfully to " + inviteDTO.getRecipientEmail()).build();

    }



//
//    @Override
//    public String sendInviteEmail(InviteDTO receiver,  Principal connectedUser, String email) {
//        MimeMessage message = mailSender.createMimeMessage();
//
//        // getting the current logged user
//        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
//
//        //user invitation email draft
//        try {
//            message.setSubject("Invitation from " + user.fullName());
//            message.setFrom("iakwasititus@gmail.com");
//            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiver.getReceiver()));
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
        byte[] bytes = new byte[5];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(bytes);

        return new BigInteger(1, bytes).toString(16); // Hexadecimal encoding
    }

   // user invitation link
    public String generateInviteLink() {
        // Use the same stable token for generating the invitation link
        return "http://localhost:5000/api/v1/auth/invite?inviteCode=" + INVITATION_TOKEN;
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

            inviteRepository.save(invite);
        }
        GenResponse.builder()
                .status(HttpStatus.ACCEPTED.value())
                .message("Invite Decline").build();
    }
}










//        if (inviteOptional.isPresent()) {
//            Invite invite = inviteOptional.get();
//
//            // Update the 'accepted' field to true
//            invite.setAccepted(true);
//            //User sender = invite.getSender();
//
//
//            User recipiant = userRepository.findByEmail(email)
//                    .orElseThrow(() -> new RuntimeException("Recipient not found"));
//
//
//
//            Contacts contacts = new Contacts();
//            contacts.setFirstName(recipiant.getFirstname());
//          //  contacts.setUser(user);
//
//       //    user.addContact(recipiant);
//        //  recipiant.addContact(user);
//
//
//
//            // Save the updated invite
//            contactRepository.save(contacts);
//            inviteRepository.save(invite);
//        //    userRepository.save(user);
//            userRepository.save(recipiant);
//
//        }else {
//            throw new RuntimeException("Invite not found");
//        }