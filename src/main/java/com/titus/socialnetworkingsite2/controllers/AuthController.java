package com.titus.socialnetworkingsite2.controllers;

import com.titus.socialnetworkingsite2.Dto.AuthenticationDTO;
import com.titus.socialnetworkingsite2.Dto.Response.GenResponse;
import com.titus.socialnetworkingsite2.Dto.RegistrationDTO;
import com.titus.socialnetworkingsite2.model.AuthTokenResponse;
import com.titus.socialnetworkingsite2.services.ServiceImpl.AuthenticationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationServiceImpl authenticationService;


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
}
