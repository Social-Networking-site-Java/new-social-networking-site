package com.titus.socialnetworkingsite2.Dto;


import com.titus.socialnetworkingsite2.Dto.Enum.InvitationStatus;
import lombok.*;

import java.math.BigInteger;
import java.security.SecureRandom;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InviteDTO {
    private String sender;
    private String receiver;
    private String token;
    private String inviteCode;
  //  private InvitationStatus status;

//    public static String getToken() {
//        byte[] bytes = new byte[5];
//        SecureRandom secureRandom = new SecureRandom();
//        secureRandom.nextBytes(bytes);
//
//        return new BigInteger(1, bytes).toString(16); // Hexadecimal encoding
//
//    }


}
