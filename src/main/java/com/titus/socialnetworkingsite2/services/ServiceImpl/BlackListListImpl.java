package com.titus.socialnetworkingsite2.services.ServiceImpl;

import com.titus.socialnetworkingsite2.Exception.GlobalExceptionHandler;
import com.titus.socialnetworkingsite2.model.BlackListList;
import com.titus.socialnetworkingsite2.model.User;
import com.titus.socialnetworkingsite2.repositories.BlackBlackListRepo;
import com.titus.socialnetworkingsite2.services.BlackListListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BlackListListImpl implements BlackListListService {

    private final BlackBlackListRepo blackBlackListRepo;


    @Override
    public String addBlackList(User user, String blackList) {


        BlackListList blackListEnt = blackBlackListRepo.findByUserAndBlacklisted(user, blackList);
        if (blackListEnt != null) {
            throw new GlobalExceptionHandler.UserAlreadyBlacklistedException("User "+user+" is already in the blacklist!");
        }


        // create a new Entry
        BlackListList blackListList = new BlackListList();
        blackListList.setUser(user);
        blackListList.setBlacklisted(blackList);
        blackBlackListRepo.save(blackListList);
        System.out.println(user);
        return "User " + blackList + " was added to blacklist By " + user;
    }

//    @Override
//    public String addBlackList2(BlackListListDTO blackListListDTO) {
//
//        BlackListList blackListList = BlackListList.builder()
//                .isBlacklisted(true)
//                .blacklisted(blackListListDTO.getBlacklisted())
//                .build();
//        return blackBlackListRepo.save(blackListList).getUser().getEmail();
//    }

//    public BlackListList addBlackList(BlackListListDTO blackListListDTO) {
//        BlackListList blackListList = BlackListList.builder()
//                .blacklisted(blackListListDTO.getBlacklisted())
//               // .user(User.builder().build())
//                .isBlacklisted(true)
//                .build();
//
//        return blackBlackListRepo.save(blackListList);
//    }

//


    @Override
    public String removeBlackList(User user, String blackList) {

        // Attempt to find the blacklisted entry for the user
        BlackListList blackListEntry = blackBlackListRepo.findByUserAndBlacklisted(user, blackList);

        // If the blacklisted entry exists, remove it
        if (blackListEntry != null) {
            blackBlackListRepo.delete(blackListEntry);
            return "You have been removed from the blacklist by " + user;
        } else {
            // If the blacklisted entry does not exist, throw an exception
            throw new NoSuchElementException("User " + user+ " is not in the blacklist!");
        }
    }




    @Override
    public List<BlackListList> getBlackListList(String blackList) {
      return   blackBlackListRepo.findAll();}
}
