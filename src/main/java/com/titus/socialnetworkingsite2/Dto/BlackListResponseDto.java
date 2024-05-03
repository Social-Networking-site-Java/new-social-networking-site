package com.titus.socialnetworkingsite2.Dto;


import com.titus.socialnetworkingsite2.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class BlackListResponseDto {
//    private int id;
//    private String blacklistedBy;
    private String blacklisted;

}
