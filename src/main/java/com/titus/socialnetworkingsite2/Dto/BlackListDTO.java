package com.titus.socialnetworkingsite2.Dto;

import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlackListDTO {

    private String blacklisted;
    private String blacklistedBy;
    private boolean isBlacklisted;



}




