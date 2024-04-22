package com.titus.socialnetworkingsite2.services;


import com.titus.socialnetworkingsite2.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Configuration
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional // when the user is loaded both the roles and the user details will be loaded
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // the [UsernameNotFoundException] will be thrown when a user is not found

        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
