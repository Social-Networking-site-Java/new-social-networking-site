package com.titus.socialnetworkingsite2.services;

import com.titus.socialnetworkingsite2.model.User;
import com.titus.socialnetworkingsite2.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class profileServices {

    private final UserRepository userRepository;

    public String resetProfile(MultipartFile file) throws IOException {

        String folder_path = "D:\\profile-reset-folder\\";
        String filePath = folder_path + file.getOriginalFilename();

        User setProfilePicture = userRepository.save(User.builder()
                .profilePictureName(file.getOriginalFilename())
                .profilePictureUrl(filePath).build());
        file.transferTo(new File(filePath));

        return "file uploaded successfully " + filePath;
        // return "Successfully Reset Profile Picture" + setProfilePicture.getProfilePictureUrl();
    }

}
