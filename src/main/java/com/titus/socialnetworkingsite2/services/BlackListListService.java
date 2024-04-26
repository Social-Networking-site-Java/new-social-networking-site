package com.titus.socialnetworkingsite2.services;

import com.titus.socialnetworkingsite2.Dto.BlackListListDTO;
import com.titus.socialnetworkingsite2.model.BlackListList;
import com.titus.socialnetworkingsite2.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BlackListListService {

    String addBlackList(User user, String blackList);
   // String addBlackList2(BlackListListDTO blackListListDTO);
    String removeBlackList(User user, String blackList);
    List<BlackListList> getBlackListList(String blackList);

}
