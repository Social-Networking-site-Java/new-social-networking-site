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
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl {

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;



    @Value("${application.mailing.frontend.activation-url}")
   // String activationUrl;
    String ACTIVATION_URL;
    String REGISTRATION_SUCCESS = "User Created Successfully. Please check email to verify your account";
    String USER_NOT_FOUND = "User already Exist";
    String PASSWORD_RESET_SUCCESSFUL = "Password Reset Successful";
    String AUTH_SUCCESS = "Authentication successful";


    // Registering the user
    public GenResponse register(RegistrationDTO request) throws MessagingException {

        var existingUser= userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            return GenResponse.builder()
                    .status(HttpStatus.CREATED.value())
                    .message(USER_NOT_FOUND).build();
        }


        var userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new IllegalStateException("ROLE USER not found"));

        var user = User.builder()
                .firstname(request.getFirstName())
                .lastname(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(userRole))
                .build();

        userRepository.save(user);

        // validation email
        sendValidationEmail(user);

       return GenResponse.builder()
               .status(HttpStatus.CREATED.value())
               .message(REGISTRATION_SUCCESS).build();
    }

    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);

        // send email
        emailService.sendEmail(
                user.getEmail(),
                user.fullName(),
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
                .expiredAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();

        tokenRepository.save(token);
        return generateToken;
    }

    private String generateActivationCode() {
        String verification_code = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i< 6; i++){
            int randomIndex = secureRandom.nextInt(verification_code.length()); // 0..9
            codeBuilder.append(verification_code.charAt(randomIndex));
        }
        return codeBuilder.toString();

    }


    // authenticating the user
    public AuthTokenResponse authenticate(AuthenticationDTO request) {

        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var claims = new HashMap<String, Object>();
        var user = ((User)auth.getPrincipal());
        claims.put("fullName", user.fullName());
        var jetToken = jwtService.generateToken(claims, user);

        return  AuthTokenResponse.builder().token(jetToken).message(AUTH_SUCCESS).build();
    }


    // activating the user account
    public String activateAccount(String token) throws MessagingException {

        Token savedToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token not found"));

        if (LocalDateTime.now().isAfter(savedToken.getExpiredAt())) {
            sendValidationEmail(savedToken.getUser());
            throw new RuntimeException("Token is expired, But a new one has been created and sent to your account.");
        }
        var user = userRepository.findById(savedToken.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setEnabled(true);
        userRepository.save(user);

        savedToken.setValidateAt(LocalDateTime.now());
        tokenRepository.save(savedToken);

        return "Account Activated Successfully!";
    }



    // change users password
    public GenResponse changePassword(ChangePasswordDTO request, Principal connectedUser) {

        if (connectedUser == null) {

//            throw new BadCredentialsException("User not authenticated");
            return GenResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(USER_NOT_FOUND).build();
        }

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
//            throw new BadCredentialsException("Wrong password");
            return GenResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("wrong password").build();
        }

        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            return GenResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("passwords do not match").build();
        }

        // update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the user object to the database
        userRepository.save(user);

        return GenResponse.builder()
                .status(HttpStatus.OK.value())
                .message(PASSWORD_RESET_SUCCESSFUL).build();


    }
}

