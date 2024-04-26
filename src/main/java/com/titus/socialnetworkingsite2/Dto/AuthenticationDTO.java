package com.titus.socialnetworkingsite2.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthenticationDTO {

    @Email(message = "Email not well formatted")
    @NotEmpty(message = "field required")
    @NotBlank(message = "field required")
    private String email;

    @NotEmpty(message = "field required")
    @NotBlank(message = "field required")
    @Size(min = 8, message = "Invalid Password")
    private String password;
}
