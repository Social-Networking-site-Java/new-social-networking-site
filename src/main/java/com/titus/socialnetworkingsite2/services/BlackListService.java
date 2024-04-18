package com.titus.socialnetworkingsite2.services;

import com.titus.socialnetworkingsite2.Dto.BlackListUsers;
import com.titus.socialnetworkingsite2.model.User;

public interface BlackListService {
    BlackListUsers addToBlacklist(BlackListUsers blackListUsers);
//    void removeFromBlacklist(User user);
//    boolean isBlacklisted(User user);
}
