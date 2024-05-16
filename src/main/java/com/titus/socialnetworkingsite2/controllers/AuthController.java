package com.titus.socialnetworkingsite2.controllers;

import com.titus.socialnetworkingsite2.Dto.AuthenticationDTO;
import com.titus.socialnetworkingsite2.Dto.Response.GenResponse;
import com.titus.socialnetworkingsite2.Dto.RegistrationDTO;
import com.titus.socialnetworkingsite2.model.AuthTokenResponse;
import com.titus.socialnetworkingsite2.services.AuthService;
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

    private final AuthService authService;

    // registering user
    @PostMapping("/register")
    @Operation(summary = "Registering a user")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<GenResponse> register(@RequestBody @Valid RegistrationDTO registerDto) throws MessagingException {
        return new ResponseEntity<>(authService.registerUser(registerDto), HttpStatus.CREATED);
    }

    // authenticate
    @PostMapping("/log-in")
    @Operation(summary = "Logging In the User")
    public ResponseEntity<AuthTokenResponse> authenticationResponseEntity(@RequestBody @Valid AuthenticationDTO authenticationDto) {
        return new ResponseEntity<>(authService.authenticateUser(authenticationDto), HttpStatus.OK);
    }

    // activate token
    @GetMapping("/activate-account{token}")
    @Operation(summary = "Account Activation")
    public ResponseEntity<String> confirm( @PathVariable(name = "token") @RequestParam String token) throws MessagingException {
    return new ResponseEntity<>(authService.activateAccount(token), HttpStatus.OK);
    }
}
