package com.titus.socialnetworkingsite2.services;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthTokenResponse {

    private String message;
    private String token;
}
