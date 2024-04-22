package com.titus.socialnetworkingsite2.services.ServiceImpl;

import com.titus.socialnetworkingsite2.Dto.InviteDTO;
import com.titus.socialnetworkingsite2.repositories.InviteRepository;
import com.titus.socialnetworkingsite2.repositories.UserRepository;
import com.titus.socialnetworkingsite2.services.UserInvitationService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInvitationServiceImpl implements UserInvitationService {

    private final JavaMailSender mailSender;
    private final UserRepository userRepository;
    private final InviteRepository inviteRepository;



    @Override
    public void createInvite(String user, InviteDTO receiver) {

        var res = userRepository.findByEmail(receiver.getSender());

        InviteDTO inviteDTO = new InviteDTO();
        inviteDTO.setSender(user);
        inviteDTO.setReceiver(String.valueOf(res));
        inviteDTO.setToken(InviteDTO.getToken());


    }

    @Override
    public String sendInviteEmail(InviteDTO receiver, String inviteLink) {
        MimeMessage message = mailSender.createMimeMessage();


        try {
            message.setSubject("Invitation to Join Our Platform!");
            message.setFrom("iakwasititus@gmail.com");
            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiver.getReceiver()));

            // Build email content
            String content = "Hi there,\n" +
                    "You have been invited to join our platform!\n" +
                    "Click the link below to accept the invitation and create your account:\n" +
                    inviteLink + "\n\n" +
                    "Thanks,\n" +
                    "The Team";
            message.setContent(content, "text/plain");

            mailSender.send(message);
            System.out.println("Invite email sent successfully to " + receiver);
        } catch (MessagingException e) {
            System.err.println("Error sending invite email: " + e.getMessage());
        }

        return "love";
    }


}
