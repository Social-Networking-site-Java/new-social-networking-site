package com.titus.socialnetworkingsite2.Dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ChangePasswordDTO {

    @NotEmpty(message = "currentPassword is required")
    @NotBlank(message = "currentPassword is required")
    @Size(min = 8, message = "Invalid Password")
    private String currentPassword;

    @NotEmpty(message = "newPassword is required")
    @NotBlank(message = "newPassword is required")
    @Size(min = 8, message = "Invalid Password")
    private String newPassword;

    @NotEmpty(message = "confirmPassword is required")
    @NotBlank(message = "confirmPassword is required")
    @Size(min = 8, message = "Invalid Password")
    private String confirmPassword;

}
