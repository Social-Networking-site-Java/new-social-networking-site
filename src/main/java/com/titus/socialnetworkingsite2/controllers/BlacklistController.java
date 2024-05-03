package com.titus.socialnetworkingsite2.controllers;

import com.titus.socialnetworkingsite2.Dto.BlackListDTO;
import com.titus.socialnetworkingsite2.Dto.Response.GenResponse;
import com.titus.socialnetworkingsite2.services.BlackListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/blacklist/")
public class BlacklistController {

    private final BlackListService blackListService;


    @PostMapping("/addToBlackList")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<GenResponse> addToBlackList(@RequestBody BlackListDTO blackListDTO) {
        return new ResponseEntity<>(blackListService.addToBlackList(blackListDTO), HttpStatus.OK);
    }

    @PostMapping("/removeFromBlacklist")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GenResponse> removeFromBlacklist(@RequestBody BlackListDTO blackList) {
        return new ResponseEntity<>(blackListService.removeFromBlacklist(blackList), HttpStatus.OK);
    }

    @GetMapping("/getAllBlackListedUsers")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getBlacklists() {
        return blackListService.getBlacklists();
    }
}
