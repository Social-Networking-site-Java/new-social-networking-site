package com.titus.socialnetworkingsite2.controllers;

import com.titus.socialnetworkingsite2.Dto.AuthenticationDTO;
import com.titus.socialnetworkingsite2.Dto.ChangePasswordDTO;
import com.titus.socialnetworkingsite2.Dto.ChangePasswordRequest;
import com.titus.socialnetworkingsite2.Dto.Response.GenResponse;
import com.titus.socialnetworkingsite2.Dto.RegistrationDTO;
import com.titus.socialnetworkingsite2.model.AuthTokenResponse;
import com.titus.socialnetworkingsite2.model.User;
import com.titus.socialnetworkingsite2.services.ServiceImpl.AuthenticationServiceImpl;
import com.titus.socialnetworkingsite2.services.ServiceImpl.profileServices;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationServiceImpl authenticationService;
    private final profileServices  profileServices;

    // registering user
    @PostMapping("/register")
    @Operation(summary = "registering a user")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<GenResponse> register(@RequestBody @Valid RegistrationDTO registerDto) throws MessagingException {
        return new ResponseEntity<>(authenticationService.register(registerDto), HttpStatus.CREATED);
    }


    // authenticate
    @PostMapping("/authentication")
    @Operation(summary = "Logging In the User")
    public ResponseEntity<AuthTokenResponse> authenticationResponseEntity(@RequestBody @Valid AuthenticationDTO authenticationDto) {
        return new ResponseEntity<>(authenticationService.authenticate(authenticationDto), HttpStatus.OK);
    }

    // activate token
    @GetMapping("/activate-account{token}")
    @Operation(summary = "Account Activation")
    public ResponseEntity<String> confirm( @PathVariable(name = "token") @RequestParam String token) throws MessagingException {
     String confirmationResponse = authenticationService.activateAccount(token);
    return new ResponseEntity<>(confirmationResponse, HttpStatus.OK);
    }


    @PatchMapping("/change-password")
    @Operation(summary = "Password Change")
    public ResponseEntity<GenResponse> changePassword(@RequestBody @Valid ChangePasswordDTO changePasswordDTO) {
        return new ResponseEntity<>( authenticationService.changePassword(changePasswordDTO ), HttpStatus.OK);
    }

    @PostMapping("/reset-profile")
    @Operation(summary = "Resetting User Profile")
    public ResponseEntity<GenResponse> profileSettings(@RequestParam("image") MultipartFile image) throws IOException {
        return new ResponseEntity<>(profileServices.resetProfile(image),HttpStatus.OK);
    }


}
