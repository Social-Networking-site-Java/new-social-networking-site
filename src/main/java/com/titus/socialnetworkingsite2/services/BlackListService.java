package com.titus.socialnetworkingsite2.services;

import com.titus.socialnetworkingsite2.Dto.BlackListResponseDto;
import com.titus.socialnetworkingsite2.Dto.BlackListUserDTO;
import com.titus.socialnetworkingsite2.model.BlackList;
import com.titus.socialnetworkingsite2.model.User;

import java.security.Principal;
import java.util.List;

public interface BlackListService {
   // BlackListResponseDto addToBlacklist(BlackListUserDTO blackListUserDTO);

    String addTo(User user, User blackListUser);
    String removeFromBlacklist(User user, User blackListUser);
    List<BlackList> getBlacklists();


}
