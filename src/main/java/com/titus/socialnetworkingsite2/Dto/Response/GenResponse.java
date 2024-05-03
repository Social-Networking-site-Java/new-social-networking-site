package com.titus.socialnetworkingsite2.Dto.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GenResponse {

    private int status;
    private String message;
}
