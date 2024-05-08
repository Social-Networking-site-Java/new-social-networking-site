package com.titus.socialnetworkingsite2.controllers.Settings;

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
@RequestMapping("/api/v1/auth")
public class SettingsController {

    private final SettingsService settingsService;


    @PatchMapping("/change-password")
    @Operation(summary = "Password Change")
    public ResponseEntity<GenResponse> changePassword(@RequestBody @Valid ChangePasswordDTO changePasswordDTO) {
        return new ResponseEntity<>( settingsService.changePassword(changePasswordDTO ), HttpStatus.OK);
    }

    @PostMapping("/reset-profile")
    @Operation(summary = "Resetting User Profile")
    public ResponseEntity<GenResponse> profileSettings(@RequestParam("image") MultipartFile image) throws IOException {
        return new ResponseEntity<>(settingsService.resetProfile(image),HttpStatus.OK);
    }
}
