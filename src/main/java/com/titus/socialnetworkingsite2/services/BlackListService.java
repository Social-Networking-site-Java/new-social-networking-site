package com.titus.socialnetworkingsite2.services;

import com.titus.socialnetworkingsite2.Dto.BlackListDTO;
import com.titus.socialnetworkingsite2.Dto.Response.GenResponse;
import com.titus.socialnetworkingsite2.model.BlackList;

import java.util.List;

public interface BlackListService {
    GenResponse addToBlackList(BlackListDTO blackListDTO);
    GenResponse removeFromBlacklist(BlackListDTO blackListDTO);
    List<BlackList> getBlacklists();
}
