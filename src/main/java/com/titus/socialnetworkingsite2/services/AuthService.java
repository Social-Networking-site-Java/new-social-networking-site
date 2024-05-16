package com.titus.socialnetworkingsite2.services;

import com.titus.socialnetworkingsite2.Dto.AuthenticationDTO;
import com.titus.socialnetworkingsite2.Dto.RegistrationDTO;
import com.titus.socialnetworkingsite2.Dto.Response.GenResponse;
import com.titus.socialnetworkingsite2.model.AuthTokenResponse;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    GenResponse registerUser(RegistrationDTO registerDto) throws MessagingException;
    AuthTokenResponse authenticateUser(AuthenticationDTO request);
    String activateAccount(String token) throws MessagingException;
}
