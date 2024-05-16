package com.titus.socialnetworkingsite2.services.ServiceImpl;


import com.titus.socialnetworkingsite2.Dto.BlackListDTO;
import com.titus.socialnetworkingsite2.Dto.Response.GenResponse;
import com.titus.socialnetworkingsite2.model.BlackList;
import com.titus.socialnetworkingsite2.repositories.BlackListRepository;
import com.titus.socialnetworkingsite2.repositories.UserRepository;
import com.titus.socialnetworkingsite2.services.BlackListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.titus.socialnetworkingsite2.Dto.Response.ResponseConstants.*;

@Service
@RequiredArgsConstructor
public class BlackListServicesImpl implements BlackListService {

    private final BlackListRepository blackListRepository;
    private final UserRepository userRepository;



    @Override
    public GenResponse addToBlackList(BlackListDTO blackListDTO) {

        // Current User
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) auth.getPrincipal();


        // check if user exist
        var blacklistedUser = userRepository.findByUsernameIgnoreCase(blackListDTO.getBlacklisted().trim());
        if (blacklistedUser.isEmpty()) {
            return GenResponse.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message(blackListDTO.getBlacklisted() + USER_NOT_FOUND)
                    .build();
        }

        System.out.println(user.getUsername());
        System.out.println(blacklistedUser);



      //  UserDetails user2 = (UserDetails) userRepository.findByUsernameIgnoreCase(blackListDTO.getBlacklisted().trim()).get();


        // Check if the recipient is blacklisted
        Optional<BlackList> existingBlacklistedUser = blackListRepository
                .findByBlacklistedAndBlacklistedBy(blacklistedUser.get().getUsername(), user.getUsername());

        if (existingBlacklistedUser.isPresent()) {
            return GenResponse.builder()
                    .status(HttpStatus.ACCEPTED.value())
                    .message(ALREADY_BLACKLISTED + blackListDTO.getBlacklisted())
                    .build();
        }


        // Create and save the BlackList object
        BlackList blackList = BlackList.builder()
                .blacklisted(blacklistedUser.get().getUsername())
                .isBlacklisted(true)
//                .blacklistedBy(blackListDTO.getBlacklistedBy())
                .blacklistedBy(user.getUsername())
                .build();

        blackListRepository.save(blackList);

        return GenResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message(SUCCESSFULLY_ADDED_TO_BLACKLIST).build();
    }

    // Remove Users from Blacklist
    @Override
    public GenResponse removeFromBlacklist(String blacklisted) {

        // Retrieve the blacklisted user from the repository
        BlackList blackListedUser = blackListRepository.findByBlacklisted(blacklisted);

        // Check if the user exists in the blacklist
        if (blackListedUser == null) {
            return GenResponse.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message(USER_NOT_FOUND_IN_BLACKLIST)
                    .build();
        }

        // Remove the user from the blacklist
        blackListRepository.delete(blackListedUser);

        return GenResponse.builder()
                .status(HttpStatus.OK.value())
                .message(blacklisted +  SUCCESSFULLY_REMOVE_FROM_BLACKLIST).build();
    }

    @Override
    public List<BlackList> getBlacklistsByUsername(String blacklistedBy) {
        return blackListRepository.findAllByBlacklistedBy(blacklistedBy);
    }
}
