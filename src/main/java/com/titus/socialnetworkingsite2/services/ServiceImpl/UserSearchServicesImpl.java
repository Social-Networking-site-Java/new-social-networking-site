package com.titus.socialnetworkingsite2.services.ServiceImpl;


import com.titus.socialnetworkingsite2.model.User;
import com.titus.socialnetworkingsite2.repositories.UserSearchRepository;
import com.titus.socialnetworkingsite2.services.UserSearchServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSearchServicesImpl implements UserSearchServices {

    private final UserSearchRepository userSearchRepository;

    @Override
    public List<User> searchUser(String searchTerm) {

        return userSearchRepository.findByEmailContainingOrFirstnameContaining
                (searchTerm, searchTerm);
    }

    @Override
    public List<User> getAllUsers() {
        return userSearchRepository.findAll();
    }
}
