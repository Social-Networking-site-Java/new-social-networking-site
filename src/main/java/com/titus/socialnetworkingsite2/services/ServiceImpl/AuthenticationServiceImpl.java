package com.titus.socialnetworkingsite2.services.ServiceImpl;


import com.titus.socialnetworkingsite2.Dto.*;
import com.titus.socialnetworkingsite2.Dto.Response.GenResponse;
import com.titus.socialnetworkingsite2.Email.EmailService;
import com.titus.socialnetworkingsite2.Email.EmailTemplateName;
import com.titus.socialnetworkingsite2.config.JwtService;
import com.titus.socialnetworkingsite2.model.User;
import com.titus.socialnetworkingsite2.repositories.RoleRepository;
import com.titus.socialnetworkingsite2.repositories.TokenRepository;
import com.titus.socialnetworkingsite2.repositories.UserRepository;
import com.titus.socialnetworkingsite2.model.AuthTokenResponse;
import com.titus.socialnetworkingsite2.services.AuthService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;

import static com.titus.socialnetworkingsite2.Dto.Response.ResponseConstants.*;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthService {

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    @Value("${application.mailing.frontend.activation-url}")
    String ACTIVATION_URL;


    // Registering the user
    @Override
    public GenResponse registerUser(RegistrationDTO request) throws MessagingException {


        var existingUser = userRepository.findByEmail(request.getEmail().trim());
        if (existingUser.isPresent()) {
            return GenResponse.builder()
                    .status(HttpStatus.CREATED.value())
                    .message(USER_ALREADY_EXIST).build();
        }

      var existingFirstname = userRepository.findByUsernameIgnoreCase(request.getUserName().trim());
        if (existingFirstname.isPresent()) {
            return GenResponse.builder()
                    .status(HttpStatus.ALREADY_REPORTED.value())
                    .message(USERNAME_IS_TAKEN).build();
        }


        var userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new IllegalStateException(ROLE_USER_NOT_FOUND));

        var user = User.builder()
                .fullName(request.getFullName())
                .username(request.getUserName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(userRole))
                .build();

        // save user
        userRepository.save(user);




        // validation email
        sendValidationEmail(user);


        // response
        return GenResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message(SIGN_UP_SUCCESS).build();
    }

    // Generate email
    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);

        // send email
        emailService.sendEmail(
                user.getEmail(),
                user.getFullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                ACTIVATION_URL,
                newToken,
                "Account Activation"
        );
    }

    private String generateAndSaveActivationToken(User user) {
        // generate token
        String generateToken = generateActivationCode();

        var token = Token.builder()
                .token(generateToken)
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusMinutes(30))
                .user(user)
                .build();

        tokenRepository.save(token);
        return generateToken;
    }

    private String generateActivationCode() {
        String verification_code = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < 6; i++) {
            int randomIndex = secureRandom
                    .nextInt(verification_code.length()); // 0..9
            codeBuilder.append(verification_code.charAt(randomIndex));
        }
        return codeBuilder.toString();

    }

@Override
public AuthTokenResponse authenticateUser(AuthenticationDTO request) {
    try {

        // Authenticate the user
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword())
        );

        // Retrieve user details
        UserDetails userDetails = (UserDetails) auth.getPrincipal();


        // Find the corresponding user in the database
        User isUser = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(USER__NOT__FOUND));


        Map<String, Object> claims = new HashMap<>();
        claims.put("FullName", isUser.getFullName());
        claims.put("UserId", isUser.getId());
        claims.put("username", isUser.getUsername());
        claims.put("Email", userDetails.getUsername());

        String jwtToken = jwtService.generateToken(claims, userDetails);


        // Return authentication success with token
        return AuthTokenResponse.builder()
                .token(jwtToken)
                .message(AUTH_SUCCESS)
                .build();

    } catch (NoSuchElementException e) {
        // User not found
        return AuthTokenResponse.builder()
                .message(USER__NOT__FOUND)
                .build();
    }
}


    // activating the user account
    @Override
    public String activateAccount(String token) throws MessagingException {

        Token savedToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException(TOKEN_NOT_FOUND));

        if (LocalDateTime.now().isAfter(savedToken.getExpiredAt())) {
            sendValidationEmail(savedToken.getUser());
            throw new RuntimeException(TOKEN_EXPIRED);
        }

        var user = userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException(USER__NOT__FOUND));
        user.setEnabled(true);
        userRepository.save(user);

        savedToken.setValidateAt(LocalDateTime.now());
        tokenRepository.save(savedToken);

        return ACCOUNT_ACTIVATION_SUCCESSFULLY;
    }



    }


