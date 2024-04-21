package com.titus.socialnetworkingsite2.Dto;


import com.titus.socialnetworkingsite2.model.User;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InviteDTO {
    private String recipientEmail;
    private String inviteCode;
    private User sender;
}
