package com.titus.socialnetworkingsite2.services.ServiceImpl;

import com.titus.socialnetworkingsite2.Dto.ChangePasswordDTO;
import com.titus.socialnetworkingsite2.Dto.Response.GenResponse;
import com.titus.socialnetworkingsite2.model.UserProfileSettings;
import com.titus.socialnetworkingsite2.repositories.ProfilePictureRepository;
import com.titus.socialnetworkingsite2.repositories.UserRepository;
import com.titus.socialnetworkingsite2.services.SettingsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import static com.titus.socialnetworkingsite2.Dto.Response.ResponseConstants.*;

@Service
@RequiredArgsConstructor
public class SettingsServiceImpl  implements SettingsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ProfilePictureRepository profilePictureRepository;



    @Override
    // change users password
    public GenResponse changePassword(ChangePasswordDTO request) {

        Authentication user = SecurityContextHolder.getContext().getAuthentication();
        var isUser = userRepository.findByEmail(user.getName()).orElseThrow();

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), isUser.getPassword() )) {
            return GenResponse.builder()
                    .status(HttpStatus.ACCEPTED.value())
                    .message(PASSWORD_IS_INCORRECT).build();
        }

        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            return GenResponse.builder()
                    .status(HttpStatus.ACCEPTED.value())
                    .message(PASSWORD_DOES_NOT_MATCH).build();
        }


        // change the password
        isUser.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the user object to the database
        userRepository.save(isUser);

        return GenResponse.builder()
                .status(HttpStatus.ACCEPTED.value())
                .message(PASSWORD_CHANGE_SUCCESS).build();
    }



    @Override
    public GenResponse changeProfile(MultipartFile file) throws IOException {

        String folder_path = "D:\\profile-reset-folder\\";
        String filePath = folder_path + file.getOriginalFilename();

//      UserProfileSettings userProfileSettings = profilePictureRepository.save(
//              UserProfileSettings.builder()
//                .profilePictureName(file.getOriginalFilename())
//                .profilePictureUrl(filePath).build());

//      UserProfileSettings userProfileSettings1 = UserProfileSettings


      UserProfileSettings userProfileSettings1 = new UserProfileSettings();
      userProfileSettings1.setProfilePictureUrl(filePath);
      userProfileSettings1.setProfilePictureName(file.getOriginalFilename());


        file.transferTo(new File(filePath));
        profilePictureRepository.save(userProfileSettings1);

        return GenResponse.builder()
                .status(HttpStatus.OK.value())
                .message("file uploaded successfully " + filePath).build();
    }
}
