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

@Service
@RequiredArgsConstructor
public class BlackListServicesImpl implements BlackListService {

    private final BlackListRepository blackListRepository;



    @Override
    public GenResponse addToBlackList(BlackListDTO blackListDTO) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails user = (UserDetails) auth.getPrincipal();


        // Check if user is already blacklisted using efficient query
        if (blackListRepository.existsByBlacklistedAndBlacklistedBy(blackListDTO.getBlacklisted(), user.getUsername())) {
            return GenResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message(blackListDTO.getBlacklisted() + " already blacklisted!")
                    .build();
        }

        System.out.println("blacklisted: " + blackListDTO.getBlacklisted());
        System.out.println("blacklistedBy: " + blackListDTO.getBlacklistedBy());
        System.out.println(blackListRepository.existsByBlacklistedAndBlacklistedBy(blackListDTO.getBlacklisted(), blackListDTO.getBlacklistedBy()));




        // blacklist User
        BlackList blackList = BlackList.builder()
                .blacklisted(blackListDTO.getBlacklisted())
                .isBlacklisted(true)
//                .blacklistedBy(blackListDTO.getBlacklistedBy())
                .blacklistedBy(user.getUsername())
                .build();
        blackListRepository.save(blackList);
        return GenResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("Successfully added to BlackList").build();
    }




    // Remove Users from Blacklist
    @Override
    public GenResponse removeFromBlacklist(BlackListDTO blackListDTO) {

        // Retrieve the blacklisted user from the repository
        BlackList blackListedUser = blackListRepository.findByBlacklisted(blackListDTO.getBlacklisted());

        // Remove the user from the blacklist
        blackListRepository.delete(blackListedUser);

        return GenResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Successfully removed from BlackList").build();
    }

    @Override
    public List<String> getBlacklists() {
        return blackListRepository.findAllBlacklisted();
    }


}
