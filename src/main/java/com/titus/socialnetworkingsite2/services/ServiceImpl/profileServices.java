package com.titus.socialnetworkingsite2.services.ServiceImpl;

import com.titus.socialnetworkingsite2.Dto.Response.GenResponse;
import com.titus.socialnetworkingsite2.model.User;
import com.titus.socialnetworkingsite2.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class profileServices {

    private final UserRepository userRepository;

    public GenResponse resetProfile(MultipartFile file) throws IOException {

        String folder_path = "D:\\profile-reset-folder\\";
        String filePath = folder_path + file.getOriginalFilename();

        User setProfilePicture = userRepository.save(User.builder()
                .profilePictureName(file.getOriginalFilename())
                .profilePictureUrl(filePath).build());
        file.transferTo(new File(filePath));

        return GenResponse.builder()
                .status(HttpStatus.OK.value())
                .message("file uploaded successfully " + filePath).build();
    }

}
