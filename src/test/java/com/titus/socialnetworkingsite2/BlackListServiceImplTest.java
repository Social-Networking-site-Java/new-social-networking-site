
package com.titus.socialnetworkingsite2;

import com.titus.socialnetworkingsite2.Dto.BlackListDTO;
import com.titus.socialnetworkingsite2.Dto.Response.GenResponse;
import com.titus.socialnetworkingsite2.model.BlackList;
import com.titus.socialnetworkingsite2.repositories.BlackListRepository;
import com.titus.socialnetworkingsite2.repositories.UserRepository;
import com.titus.socialnetworkingsite2.services.ServiceImpl.BlackListServicesImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static com.titus.socialnetworkingsite2.Dto.Response.ResponseConstants.SUCCESSFULLY_ADDED_TO_BLACKLIST;
import static com.titus.socialnetworkingsite2.Dto.Response.ResponseConstants.SUCCESSFULLY_REMOVE_FROM_BLACKLIST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BlackListServiceImplTest {

    @Mock
    private BlackListRepository blackListRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BlackListServicesImpl blackListServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddToBlackList() {
        BlackListDTO blackListDTO = new BlackListDTO();
        blackListDTO.setBlacklisted("James");

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("currentUser");

        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authentication);

       // when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(userDetails));
        when(blackListRepository.findByBlacklistedAndBlacklistedBy("James", "currentUser")).thenReturn(Optional.empty());

        GenResponse response = blackListServices.addToBlackList(blackListDTO);

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals(SUCCESSFULLY_ADDED_TO_BLACKLIST, response.getMessage());

        verify(blackListRepository, times(1)).save(any(BlackList.class));
    }

    @Test
    void testRemoveFromBlacklist() {
        BlackList blackListedUser = new BlackList();
        blackListedUser.setBlacklisted("James");

        when(blackListRepository.findByBlacklisted("James")).thenReturn(blackListedUser);

        GenResponse response = blackListServices.removeFromBlacklist("James");

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(SUCCESSFULLY_REMOVE_FROM_BLACKLIST, response.getMessage());

        verify(blackListRepository, times(1)).delete(blackListedUser);
    }
}

