package com.titus.socialnetworkingsite2.services;

import com.titus.socialnetworkingsite2.model.User;
import com.titus.socialnetworkingsite2.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserDetailImpl implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email.trim().toLowerCase());

        if (user.isEmpty())
            throw new UsernameNotFoundException(email + " not found");

        User userDetails = user.get();

        return new org.springframework.security.core.userdetails.User(
                userDetails.getEmail(),
                userDetails.getPassword(),
               userDetails.getAuthorities()
              // grantedAuthorities(userDetails)
    );

//        return User.builder()
//                .email(userDetails.getEmail())
//                .password(userDetails.getPassword())
//                .roles(grantedAuthorities())
//                .build();

    }

    private List<SimpleGrantedAuthority> grantedAuthorities(User user) {
        return Collections.singletonList(new SimpleGrantedAuthority(user.getRoles().toString()));
    }


}
