package com.titus.socialnetworkingsite2.services.ServiceImpl;


import com.titus.socialnetworkingsite2.Dto.BlackListDTO;
import com.titus.socialnetworkingsite2.Dto.Response.GenResponse;
import com.titus.socialnetworkingsite2.model.BlackList;
import com.titus.socialnetworkingsite2.model.User;
import com.titus.socialnetworkingsite2.repositories.BlackListRepository;
import com.titus.socialnetworkingsite2.services.BlackListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlackListServicesImpl implements BlackListService {



    private final BlackListRepository blackListRepository;

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
    public GenResponse addToBlackList(BlackListDTO blackListDTO) {
        // Check if blackListUser is already in the blacklist


        if (blackListRepository.existsByBlacklisted(blackListDTO.getBlacklisted())){
            return GenResponse.builder()
                    .status(HttpStatus.OK.value())
                    .message( blackListDTO.getBlacklisted() + " already blacklisted!").build();
        }


        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        BlackList blackList = BlackList.builder()
                .blacklisted(blackListDTO.getBlacklisted())
                .isBlacklisted(true)
                // .blacklistedBy(blackListDTO.getBlacklistedBy())
                .blacklistedBy(user.getId().toString())
                .build();
        blackListRepository.save(blackList);
        return GenResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("Successfully added to BlackList").build();
    }



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



















//    public String removeFromBlacklist(BlackListDTO blackListDTO) {
//
//        BlackListDTO blackList = blacklistrepo.findByBlacklisted(blackListDTO.getBlacklisted());
//
//        if (blackList != null) {
//            blacklistrepo.delete(blackList);
//        }
//        return "You have been removed from BlackList by ";
//    }

    @Override
    public List<BlackList> getBlacklists() {
        return blackListRepository.findAll();
    }

//    public List<BlackList> getBlacklists(Integer blacklistedUser) {
//        System.out.println("=======================");
//
//      //  return blacklistrepo.findAllById(blacklistedUser);
//        return blacklistrepo.ex
//    }


//    public List<BlackList> getBlacklists() {
//      //  return blacklistrepo.findAll();
//
//
//       return blacklistrepo.findAll();
//
//        //return blacklistrepo.findAllByBlacklistedUser(blacklistUserId);
//
//    }

}
