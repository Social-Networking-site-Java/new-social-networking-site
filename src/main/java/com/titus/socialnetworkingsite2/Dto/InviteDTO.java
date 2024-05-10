package com.titus.socialnetworkingsite2.Dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InviteDTO {
   // private Integer id;
    private String sender;
    private String recipientEmail;
    private String inviteCode;
    private boolean accepted;
}
