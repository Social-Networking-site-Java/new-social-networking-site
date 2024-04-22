package com.titus.socialnetworkingsite2.services;

import com.titus.socialnetworkingsite2.Dto.InviteDTO;
import com.titus.socialnetworkingsite2.model.User;

import java.security.Principal;

public interface UserInvitationService {

    String sendInviteEmail(InviteDTO receiver, String inviteLink, Principal connectedUser);

    void createInvite(User user, InviteDTO receiver);
}
