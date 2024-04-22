package com.titus.socialnetworkingsite2.services;

import com.titus.socialnetworkingsite2.Dto.InviteDTO;

public interface UserInvitationService {

    String sendInviteEmail(InviteDTO receiver, String inviteLink);

    void createInvite(String user, InviteDTO receiver);
}
