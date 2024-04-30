package com.titus.socialnetworkingsite2.controllers;

import com.titus.socialnetworkingsite2.Dto.AuthenticationDTO;
import com.titus.socialnetworkingsite2.Dto.ChangePasswordDTO;
import com.titus.socialnetworkingsite2.Dto.Response.GenResponse;
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
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationServiceImpl authenticationService;
    private final profileServices  profileServices;

    // registering user
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<GenResponse> register(@RequestBody @Valid RegistrationDTO registerDto) throws MessagingException {
        return new ResponseEntity<>(authenticationService.register(registerDto), HttpStatus.CREATED);
    }



    // authenticate
    @PostMapping("/authentication")
    public ResponseEntity<AuthTokenResponse> authenticationResponseEntity(@RequestBody @Valid AuthenticationDTO authenticationDto) {
        return new ResponseEntity<>(authenticationService.authenticate(authenticationDto), HttpStatus.OK);
    }

    // activate token
    @GetMapping("/activate-account{token}")
    public ResponseEntity<String> confirm( @PathVariable(name = "token") @RequestParam String token) throws MessagingException {
     String confirmationResponse = authenticationService.activateAccount(token);
    return new ResponseEntity<>(confirmationResponse, HttpStatus.OK);
    }


    // password reset
    @PatchMapping("/reset-password")
    public ResponseEntity<GenResponse> resetPassword(@RequestBody @Valid ChangePasswordDTO changePasswordDTO, Principal principal) {
        try {

           // return ResponseEntity.ok("Password reset successful");
            return new ResponseEntity<>(authenticationService.changePassword(changePasswordDTO, principal),HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
    }

    @PostMapping("/reset-profile")
    public ResponseEntity<GenResponse> profileSettings(@RequestParam("image") MultipartFile image) throws IOException {
        return new ResponseEntity<>(profileServices.resetProfile(image),HttpStatus.OK);
    }





    @GetMapping("/welcome")
    @PreAuthorize("isAuthenticated()")
    public String welcome(){
        return "welcome";
    }


}
