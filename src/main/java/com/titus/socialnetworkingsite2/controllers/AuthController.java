package com.titus.socialnetworkingsite2.controllers;

import com.titus.socialnetworkingsite2.Dto.AuthenticationDTO;
import com.titus.socialnetworkingsite2.Dto.ChangePasswordDTO;
import com.titus.socialnetworkingsite2.Dto.RegistrationDTO;
import com.titus.socialnetworkingsite2.model.AuthTokenResponse;
import com.titus.socialnetworkingsite2.services.ServiceImpl.AuthenticationServiceImpl;
import com.titus.socialnetworkingsite2.services.ServiceImpl.profileServices;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthController {

    private final AuthenticationServiceImpl authenticationService;
    private final profileServices  profileServices;

    // registering user
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> register(@RequestBody @Valid RegistrationDTO registerDto) throws MessagingException {
      String registrationResponse =   authenticationService.register(registerDto);
        return new ResponseEntity<>(registrationResponse, HttpStatus.CREATED);
    }

    // authenticate
    @PostMapping("/authentication")
    public ResponseEntity<AuthTokenResponse> authenticationResponseEntity(
            @RequestBody @Valid AuthenticationDTO authenticationDto) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationDto));
    }

    // activate token
    @GetMapping("/activate-account{token}")
    public ResponseEntity<String> confirm( @PathVariable(name = "token") @RequestParam String token) throws MessagingException {
     String confirmationResponse = authenticationService.activateAccount(token);
    return new ResponseEntity<>(confirmationResponse, HttpStatus.OK);
    }


    // password reset
    @PatchMapping("/reset-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> resetPassword(@RequestBody ChangePasswordDTO changePasswordDTO, Principal principal) {
        try {
            authenticationService.changePassword(changePasswordDTO, principal);
            return ResponseEntity.ok("Password reset successful");
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }



    @PostMapping("/reset-profile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> profileSettings(@RequestParam("image") MultipartFile image) throws IOException {
     String setProfile =    profileServices.resetProfile(image);
        return ResponseEntity.status(HttpStatus.OK).body(setProfile);
    }





    @GetMapping("/welcome")
    @PreAuthorize("isAuthenticated()")
    public String welcome(){
        return "welcome";
    }


}
