package com.titus.socialnetworkingsite2.controllers;

import com.titus.socialnetworkingsite2.Dto.ChangePasswordDTO;
import com.titus.socialnetworkingsite2.Dto.Response.GenResponse;
import com.titus.socialnetworkingsite2.services.SettingsService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/settings")
public class SettingsController {

    private final SettingsService settingsService;


    @PutMapping("/change-password")
    @Operation(summary = "Password Change")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<GenResponse> changePassword(@RequestBody @Valid ChangePasswordDTO changePasswordDTO) {
        return new ResponseEntity<>( settingsService.changePassword(changePasswordDTO ), HttpStatus.ACCEPTED);
    }

    @PostMapping("/change-profile")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Resetting User Profile")
    public ResponseEntity<GenResponse> profileSettings(@RequestParam("image") MultipartFile image) throws IOException {
        return new ResponseEntity<>(settingsService.changeProfile(image),HttpStatus.ACCEPTED);
    }
}
