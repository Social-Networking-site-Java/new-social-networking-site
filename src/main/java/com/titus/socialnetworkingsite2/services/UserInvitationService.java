package com.titus.socialnetworkingsite2.services;

import com.titus.socialnetworkingsite2.Dto.InviteDTO;
import com.titus.socialnetworkingsite2.model.Invite;
import com.titus.socialnetworkingsite2.model.User;

import java.security.Principal;
import java.util.List;

public interface UserInvitationService {

    //String sendInviteEmail(InviteDTO receiver, String inviteLink, Principal connectedUser);
    String sendInviteEmail(InviteDTO receiver, Principal connectedUser);

    void createInvite(User user, InviteDTO receiver, String inviteToken);

    List<Invite> getAllInvitations();

    void acceptInvite(String inviteCode);
    void declineInvite(String inviteCode);
}
