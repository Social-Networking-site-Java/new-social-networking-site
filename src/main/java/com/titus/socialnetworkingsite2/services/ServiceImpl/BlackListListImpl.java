//package com.titus.socialnetworkingsite2.services.ServiceImpl;
//
//import com.titus.socialnetworkingsite2.Dto.BlackListListDTO;
//import com.titus.socialnetworkingsite2.Dto.Response.GenResponse;
//import com.titus.socialnetworkingsite2.Exception.GlobalExceptionHandler;
//import com.titus.socialnetworkingsite2.model.BlackListList;
//import com.titus.socialnetworkingsite2.model.User;
//import com.titus.socialnetworkingsite2.repositories.BlackBlackListRepo;
//import com.titus.socialnetworkingsite2.services.BlackListListService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.NoSuchElementException;
//
//@Service
//@RequiredArgsConstructor
//public class BlackListListImpl implements BlackListListService {
//
//    private final BlackBlackListRepo blackBlackListRepo;
//
//
//    @Override
//    public void addBlackList(User user, BlackListListDTO blackList) {
//
//
//        BlackListList blackListEnt = blackBlackListRepo.findByBlacklisted(blackList);
//        if (blackListEnt != null) {
//            throw new GlobalExceptionHandler.UserAlreadyBlacklistedException("Already in the blacklist!");
//        }
//
//        // create a new Entry
//        BlackListList blackListList = new BlackListList();
//        blackListList.setUser(user);
//        blackBlackListRepo.save(blackListList);
//
//        GenResponse.builder().message("User successfully added to blacklist").build();
//
//    }
//
//
//    @Override
//    public String removeBlackList(User user, String blackList) {
//
//        // Attempt to find the blacklisted entry for the user
//        BlackListList blackListEntry = blackBlackListRepo.findByUserAndBlacklisted(user, blackList);
//
//        // If the blacklisted entry exists, remove it
//        if (blackListEntry != null) {
//            blackBlackListRepo.delete(blackListEntry);
//            return "You have been removed from the blacklist by " + user;
//        } else {
//            // If the blacklisted entry does not exist, throw an exception
//            throw new NoSuchElementException("User " + user+ " is not in the blacklist!");
//        }
//    }
//
//
//
//
//    @Override
//    public List<BlackListList> getBlackListList(String blackList) {
//      return   blackBlackListRepo.findAll();}
//}
