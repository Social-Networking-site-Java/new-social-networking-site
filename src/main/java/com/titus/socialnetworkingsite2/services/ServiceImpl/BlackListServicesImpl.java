package com.titus.socialnetworkingsite2.services.ServiceImpl;


import com.titus.socialnetworkingsite2.Dto.BlackListUsers;
import com.titus.socialnetworkingsite2.model.User;
import com.titus.socialnetworkingsite2.repositories.BlacklistRepository;
import com.titus.socialnetworkingsite2.repositories.RoleRepository;
import com.titus.socialnetworkingsite2.repositories.UserRepository;
import com.titus.socialnetworkingsite2.services.BlackListService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlackListServicesImpl implements BlackListService {


    private final BlacklistRepository blacklistRepository;
    private final ModelMapper modelMapper;



    // add to blacklist
    @Override
    public BlackListUsers addToBlacklist(BlackListUsers blackListUsers) {
       User myUser = modelMapper.map(blackListUsers, BlackListUsers.class).getUser();
      User save =  blacklistRepository.save(myUser);

        return modelMapper.map(save, BlackListUsers.class);
    }

    // remove from blacklist
}
