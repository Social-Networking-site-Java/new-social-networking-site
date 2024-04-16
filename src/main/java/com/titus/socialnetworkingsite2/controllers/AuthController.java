package com.titus.socialnetworkingsite2.controllers;

import com.titus.socialnetworkingsite2.Dto.AuthenticationDTO;
import com.titus.socialnetworkingsite2.Dto.RegistrationDTO;
import com.titus.socialnetworkingsite2.services.AuthTokenResponse;
import com.titus.socialnetworkingsite2.services.ServiceImpl.AuthenticationService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> register(@RequestBody @Valid RegistrationDTO registerDto) throws MessagingException {
      String registrationResponse =   authenticationService.register(registerDto);
        return new ResponseEntity<>(registrationResponse, HttpStatus.CREATED);

    }

    @PostMapping("/authentication")
    public ResponseEntity<AuthTokenResponse> authenticationResponceResponseEntity(
            @RequestBody @Valid AuthenticationDTO authenticationDto) {
        return ResponseEntity.ok(authenticationService.authenticate(authenticationDto));
    }

    @GetMapping("/activate-account{token}")
    public ResponseEntity<String> confirm( @PathVariable(name = "token")   @RequestParam String token) throws MessagingException {
     String confirmationResponse = authenticationService.activateAccount(token);
    return new ResponseEntity<>(confirmationResponse, HttpStatus.OK);
    }

    @GetMapping("/welcome")
    public String welcome(Authentication authentication){
        //String auth =  authentication.getPrincipal();
        return (String) authentication.getPrincipal();
    }




    @GetMapping("/user")
    public Principal showUser(Principal p) {
        System.out.println(p.getClass().getName());
        return p;
    }


}
