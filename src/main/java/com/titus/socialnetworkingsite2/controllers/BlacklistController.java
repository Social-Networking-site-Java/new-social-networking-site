package com.titus.socialnetworkingsite2.controllers;

import com.titus.socialnetworkingsite2.Dto.BlackListDTO;
import com.titus.socialnetworkingsite2.model.BlackList;
import com.titus.socialnetworkingsite2.repositories.BlacklistRepository;
import com.titus.socialnetworkingsite2.repositories.UserRepository;
import com.titus.socialnetworkingsite2.services.BlackListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("auth")
public class BlacklistController {

    private final BlackListService blackListService;
    private final UserRepository userRepository;
    private final BlacklistRepository blacklistRepository;



//    // Endpoint for adding a user to the blacklist
//    @PostMapping("/add")
//    public ResponseEntity<BlackListResponseDto> addToBlacklist( @RequestBody BlackListUserDTO blackListUserDTO) {
//
//        blackListService.addToBlacklist(blackListUserDTO);
//        return ResponseEntity.ok().build();
//    }


    @PostMapping("/add")
        public ResponseEntity<String> addTo( @RequestBody BlackListDTO blackListDTO) {

     String blackListResponse =  blackListService.addTo(blackListDTO.getBlacklistedUser(),blackListDTO.getUser());

        return new ResponseEntity<>(blackListResponse, HttpStatus.CREATED);

    }

    @PostMapping("/removeFromBlacklist")
    public ResponseEntity<String> removeFromBlacklist(@RequestBody BlackListDTO blackListDTO) {
     String removeFromBlackListResponse =  blackListService.removeFromBlacklist(blackListDTO.getBlacklistedUser(),blackListDTO.getUser());
       // return ResponseEntity.ok().build();
        return new ResponseEntity<>(removeFromBlackListResponse, HttpStatus.ACCEPTED);

    }

    @GetMapping("/getAllBlackList")
    public ResponseEntity<List<BlackList>> getBlacklists() {
       List<BlackList> blackLists = blackListService.getBlacklists();
       return new ResponseEntity<>(blackLists, HttpStatus.OK);

    }




   // Endpoint for removing a user from the blacklist
    //....

    // Endpoint for checking if a user is blacklisted
    //....



}
