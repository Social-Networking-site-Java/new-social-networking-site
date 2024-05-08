package com.titus.socialnetworkingsite2.services;

import com.titus.socialnetworkingsite2.Dto.ChangePasswordDTO;
import com.titus.socialnetworkingsite2.Dto.Response.GenResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface SettingsService {
    GenResponse changePassword(ChangePasswordDTO changePasswordDTO);

    GenResponse changeProfile(MultipartFile image) throws IOException;
}
