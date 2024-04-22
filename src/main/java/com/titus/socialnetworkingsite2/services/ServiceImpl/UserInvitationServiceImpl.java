package com.titus.socialnetworkingsite2.services.ServiceImpl;

import com.titus.socialnetworkingsite2.Dto.InviteDTO;
import com.titus.socialnetworkingsite2.model.Invite;
import com.titus.socialnetworkingsite2.model.User;
import com.titus.socialnetworkingsite2.repositories.InviteRepository;
import com.titus.socialnetworkingsite2.repositories.UserRepository;
import com.titus.socialnetworkingsite2.services.UserInvitationService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserInvitationServiceImpl implements UserInvitationService {

    private final JavaMailSender mailSender;
    private final UserRepository userRepository;
    private final InviteRepository inviteRepository;



    @Override
    public void createInvite(User user, InviteDTO receiver) {

        var res = userRepository.findByEmail(receiver.getReceiver());
        if (res.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }



        Optional<Invite> existingInvite = inviteRepository.findByRecipientEmail(String.valueOf(receiver));
        if (existingInvite.isPresent()) {
            throw new RuntimeException("Invite already sent to this email");
        }

//        InviteDTO inviteDTO = new InviteDTO();
//        inviteDTO.setSender(user);
//        inviteDTO.setReceiver(String.valueOf(res));
//        inviteDTO.setToken(InviteDTO.getToken());

        Invite invite = new Invite();
        invite.setSender(user);
        invite.setRecipientEmail(receiver.getReceiver());
        invite.setInviteCode(InviteDTO.getToken());

        inviteRepository.save(invite);




    }

    @Override
    public String sendInviteEmail(InviteDTO receiver, String inviteLink, Principal connectedUser) {
        MimeMessage message = mailSender.createMimeMessage();
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();


        try {
            message.setSubject("Invitation from " + user.fullName());
            message.setFrom("iakwasititus@gmail.com");
            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiver.getReceiver()));

            // Build email content
            String content = "Hi there,\n\n" +
                    "I invite you to connect with me\n" +
                    "Please Click the link below to accept my invitation\n\n" +
                    inviteLink + "\n\n" +
                    "Thanks,\n" +
                    user.getFirstname();
            message.setContent(content, "text/plain");

            mailSender.send(message);
            System.out.println("Invite email sent successfully to " + receiver.getReceiver() +" from " + user.fullName());
        } catch (MessagingException e) {
            System.err.println("Error sending invite email: " + e.getMessage());
        }

        return "love";
    }


}
