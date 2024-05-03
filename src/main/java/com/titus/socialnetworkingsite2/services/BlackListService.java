package com.titus.socialnetworkingsite2.services;

import com.titus.socialnetworkingsite2.Dto.BlackListDTO;
import com.titus.socialnetworkingsite2.Dto.Response.GenResponse;

import java.util.List;

public interface BlackListService {
    GenResponse addToBlackList(BlackListDTO blackListDTO);
    GenResponse removeFromBlacklist(BlackListDTO blackListDTO);
    List<String> getBlacklists();
}
