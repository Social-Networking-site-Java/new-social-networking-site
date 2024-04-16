package com.titus.socialnetworkingsite2.Dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SetProfilePicture {

    private String name;
    private String urlPath;
    private String type;

}
