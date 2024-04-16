package com.titus.socialnetworkingsite2.Dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ChangePasswordDTO {

    private String currentPassword;
    private String newPassword;
    private String confirmPassword;

}
