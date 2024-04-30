//package com.titus.socialnetworkingsite2.controllers;
//
//
//import com.titus.socialnetworkingsite2.Dto.BlackListListDTO;
//import com.titus.socialnetworkingsite2.Dto.Response.GenResponse;
//import com.titus.socialnetworkingsite2.model.BlackListList;
//import com.titus.socialnetworkingsite2.model.User;
//import com.titus.socialnetworkingsite2.services.BlackListListService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//
//import java.security.Principal;
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("auth")
//public class BlackListListController {
//
//    private final BlackListListService blackListListService;
//
//
//    @PostMapping("/addBlackList")
//    public ResponseEntity<GenResponse> createBlackListList(@RequestBody User user, BlackListListDTO blackListListDTO) {
//
//        blackListListService.addBlackList(user, blackListListDTO);
//
//        return ResponseEntity.status(HttpStatus.CREATED).build();
//
//   // return  ResponseEntity<>(blackListListService.addBlackList(user, blackListListDTO), blackListListService.addBlackList(user, blackListListDTO));
//
//    //return ResponseEntity.ok(blackListR);
//    }
//
////    @PostMapping("/book")
////    public ResponseEntity<String> registerBook(@RequestBody BlackListListDTO blackListListDTO){
////        return new ResponseEntity<>(blackListListService.addBlackList2(blackListListDTO), HttpStatus.CREATED);
////    }
//
//
//
//
//    @PostMapping("/removeFromBlackList")
//    public ResponseEntity<String> removeBlackList(@RequestBody BlackListListDTO blackListListDTO) {
//
//        System.out.println("==================================");
//        System.out.println("removeBlackList");
//        System.out.println(blackListListDTO.getUser());
//        System.out.println(blackListListDTO.getBlacklisted());
//        String removeRES = blackListListService.removeBlackList(blackListListDTO.getUser(),blackListListDTO.getBlacklisted());
//        return ResponseEntity.ok(removeRES);
//    }
//
//
//    @GetMapping("/getAllBlackListedUsers")
//        public List<BlackListList> getBlackListList(String blackList) {
//       // blackListListService.getBlackListList( blackList);
//        return blackListListService.getBlackListList( blackList);
//    }
//}
