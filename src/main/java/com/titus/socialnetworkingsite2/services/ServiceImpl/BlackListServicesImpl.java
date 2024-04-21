package com.titus.socialnetworkingsite2.services.ServiceImpl;


import com.titus.socialnetworkingsite2.Exception.GlobalExceptionHandler;
import com.titus.socialnetworkingsite2.model.BlackList;
import com.titus.socialnetworkingsite2.model.BlackListUser;
import com.titus.socialnetworkingsite2.model.User;
import com.titus.socialnetworkingsite2.repositories.BlackBlackListRepository;
import com.titus.socialnetworkingsite2.repositories.BlacklistRepository;
import com.titus.socialnetworkingsite2.repositories.UserRepository;
import com.titus.socialnetworkingsite2.services.BlackListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlackListServicesImpl implements BlackListService {


    private final BlacklistRepository blacklistRepository;
    private final BlackBlackListRepository blacklistrepo;
    //private final ModelMapper modelMapper;
    private UserRepository userRepository;



    // add to blacklist
//    @Override
//    public BlackListResponseDto addToBlacklist(BlackListUserDTO blackListUserDTO) {
//
//        //  var res = modelMapper.map(blackListUserDTO, BlackListUser.class);
//        //  return blacklistRepository.save(res);
//
//
//        BlackListUser blackListUser = BlackListUser.builder()
//                .blacklistedBy(blackListUserDTO.getBlacklistedBy())
//                .blacklisted(blackListUserDTO.getBlacklisted())
//                //.isBlacklisted(blackListUserDTO.isBlacklisted())
//                .build();
//
//        BlackListUser res = blacklistRepository.save(blackListUser);
//        return BlackListResponseDto.builder()
//                .id(res.getId())
//                .blacklisted(res.getBlacklisted())
//                .blacklistedBy(res.getBlacklistedBy())
//                .build();
//    }

    @Override
    public String addTo(User user, User blackListUser) {
        // Check if blackListUser is already in the blacklist
        if (blacklistrepo.existsByUserAndBlacklistedUser(user, blackListUser)) {
            throw new GlobalExceptionHandler.UserAlreadyBlacklistedException("User is already in the blacklist!");
        }


        // Create a new BlackList entity
        BlackList blackList = new BlackList();
        blackList.setUser(user);
        blackList.setBlacklistedUser(blackListUser);

        // Save the BlackList entity to the database
        blacklistrepo.save(blackList);

        // Return a response message
        return "User with ID: " + blackListUser.getId() + " was added to blacklist!";
    }

    public String removeFromBlacklist(User user, User blacklistedUser) {
        BlackList blackList = blacklistrepo.findByUserAndBlacklistedUser(user, blacklistedUser);
        if (blackList != null) {
            blacklistrepo.delete(blackList);
        }
        return "You have been removed from BlackList by" + user.getId();
    }

    public List<BlackList> getBlacklists() {
        return blacklistrepo.findAll();
    }

}
