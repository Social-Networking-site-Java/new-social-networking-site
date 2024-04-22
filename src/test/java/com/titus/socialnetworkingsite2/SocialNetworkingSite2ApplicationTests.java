//package com.titus.socialnetworkingsite2;
//
////import org.junit.jupiter.api.Test;
////import org.springframework.boot.test.context.SpringBootTest;
////
////@SpringBootTest
////class SocialNetworkingSite2ApplicationTests {
////
////	@Test
////	void contextLoads() {
////	}
////
////}
//
//
//
//
//import com.titus.socialnetworkingsite2.Dto.AuthenticationDTO;
//import com.titus.socialnetworkingsite2.Dto.RegistrationDTO;
//import com.titus.socialnetworkingsite2.Dto.Role;
//import com.titus.socialnetworkingsite2.Dto.Token;
//import com.titus.socialnetworkingsite2.Email.EmailService;
//import com.titus.socialnetworkingsite2.Email.EmailTemplateName;
//import com.titus.socialnetworkingsite2.config.JwtService;
//
//import com.titus.socialnetworkingsite2.model.User;
//import com.titus.socialnetworkingsite2.repositories.RoleRepository;
//import com.titus.socialnetworkingsite2.repositories.TokenRepository;
//import com.titus.socialnetworkingsite2.repositories.UserRepository;
//import com.titus.socialnetworkingsite2.model.AuthTokenResponse;
//import com.titus.socialnetworkingsite2.services.ServiceImpl.AuthenticationService;
//import jakarta.mail.MessagingException;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//public class AuthenticationServiceTest {
//
//	@Mock
//	private RoleRepository roleRepository;
//
//	@Mock
//	private PasswordEncoder passwordEncoder;
//
//	@Mock
//	private UserRepository userRepository;
//
//	@Mock
//	private TokenRepository tokenRepository;
//
//	@Mock
//	private EmailService emailService;
//
//	@Mock
//	private AuthenticationManager authenticationManager;
//
//	@Mock
//	private JwtService jwtService;
//
//	@InjectMocks
//	private AuthenticationService authenticationService;
//
//	private User user;
//
//	@BeforeEach
//	public void init() {
//		MockitoAnnotations.initMocks(this);
//
//		Role userRole = new Role();
//		userRole.setName("USER");
//
//		user = User.builder()
//				.firstname("John")
//				.lastname("Doe")
//				.email("john.doe@example.com")
//				.password("password")
//				.accountLocked(false)
//				.enabled(false)
//				.roles(List.of(userRole))
//				.build();
//	}
//
//	@Test
//	public void testRegister() throws MessagingException {
//		when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
//		when(roleRepository.findByName("USER")).thenReturn(Optional.of(user.getRoles().get(0)));
//		when(passwordEncoder.encode(user.getPassword())).thenReturn(user.getPassword());
//		when(userRepository.save(user)).thenReturn(user);
//
//		String result = authenticationService.register(new RegistrationDTO(user.getFirstname(), user.getLastname(), user.getEmail(), user.getPassword()));
//
//		verify(userRepository, times(1)).findByEmail(user.getEmail());
//		verify(roleRepository, times(1)).findByName("USER");
//		verify(passwordEncoder, times(1)).encode(user.getPassword());
//		verify(userRepository, times(1)).save(user);
//
//		Assertions.assertEquals("User John Created Successfully check your email john.doe@example.com and verify your email address", result);
//	}
//
//	@Test
//	public void testRegister_existingUser() {
//		when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
//
//		String result = authenticationService.register(new RegistrationDTO(user.getFirstname(), user.getLastname(), user.getEmail(), user.getPassword()));
//
//		verify(userRepository, times(1)).findByEmail(user.getEmail());
//
//		Assertions.assertEquals("User John Already Exist", result);
//	}
//
//	@Test
//	public void testAuthenticate() throws Exception {
//		when(authenticationManager.authenticate(any())).thenReturn(null);
//		when(jwtService.generateToken(any(), any())).thenReturn("token");
//
//		AuthTokenResponse result = authenticationService.authenticate(new AuthenticationDTO(user.getEmail(), user.getPassword()));
//
//		verify(authenticationManager, times(1)).authenticate(any());
//		verify(jwtService, times(1)).generateToken(any(), any());
//
//		Assertions.assertEquals("token", result.getToken());
//		Assertions.assertEquals("Authentication successful", result.getMessage());
//	}
//
//	@Test
//	public void testActivateAccount() throws MessagingException {
//		Token token = new Token();
//		token.setToken("token");
//		token.setCreatedAt(LocalDateTime.now());
//		token.setExpiredAt(LocalDateTime.now().plusMinutes(15));
//		token.setUser(user);
//
//		when(tokenRepository.findByToken("token")).thenReturn(Optional.of(token));
//		when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
//
//		String result = authenticationService.activateAccount("token");
//
//		verify(tokenRepository, times(1)).findByToken("token");
//		verify(userRepository, times(1)).findById(user.getId());
//		verify(userRepository, times(1)).save(user);
//		verify(tokenRepository, times(1)).save(token);
//
//		Assertions.assertEquals("Account Activated Successfully! \n Hmmm!!! The way i have suffered &#x1F491", result);
//	}
//
//	@Test
//	public void testActivateAccount_expiredToken() throws MessagingException {
//		Token token = new Token();
//		token.setToken("token");
//		token.setCreatedAt(LocalDateTime.now().minusMinutes(16));
//		token.setExpiredAt(LocalDateTime.now().minusMinutes(15));
//		token.setUser(user);
//
//		when(tokenRepository.findByToken("token")).thenReturn(Optional.of(token));
//
//		Assertions.assertThrows(RuntimeException.class, () -> authenticationService.activateAccount("token"));
//	}
//}
