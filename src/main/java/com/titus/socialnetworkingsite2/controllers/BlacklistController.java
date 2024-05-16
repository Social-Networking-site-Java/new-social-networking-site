package com.titus.socialnetworkingsite2.controllers;

import com.titus.socialnetworkingsite2.Dto.BlackListDTO;
import com.titus.socialnetworkingsite2.Dto.Response.GenResponse;
import com.titus.socialnetworkingsite2.model.BlackList;
import com.titus.socialnetworkingsite2.services.BlackListService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/blacklist")
public class BlacklistController {

    private final BlackListService blackListService;


    // add a user to blacklist
    @PostMapping("/add-to-blackList")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Blacklisting a user")
    public ResponseEntity<GenResponse> addToBlackList(@RequestBody BlackListDTO blackListDTO) {
        return new ResponseEntity<>(blackListService.addToBlackList(blackListDTO), HttpStatus.OK);
    }

    @DeleteMapping("/remove-from-blacklist/{blacklisted}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "removing a user from blacklist")
    public ResponseEntity<GenResponse> removeFromBlacklist(@PathVariable String blacklisted) {
        return new ResponseEntity<>(blackListService.removeFromBlacklist(blacklisted), HttpStatus.OK);
    }


    @GetMapping("/blacklisted-users/{blacklistedBy}")
    @Operation(summary = "Getting all blacklisted users by current User")
    public ResponseEntity<List<BlackList>> getBlacklistedUsers(@PathVariable String blacklistedBy) {
        return new ResponseEntity<>(blackListService.getBlacklistsByUsername(blacklistedBy), HttpStatus.OK);
    }
}
