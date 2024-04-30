package com.titus.socialnetworkingsite2.services;

import com.titus.socialnetworkingsite2.Dto.Response.GenResponse;
import com.titus.socialnetworkingsite2.Dto.InviteDTO;
import com.titus.socialnetworkingsite2.model.Invite;

import java.util.List;

public interface UserInvitationService {

    //String sendInviteEmail(InviteDTO receiver, String inviteLink, Principal connectedUser);
   // String sendInviteEmail(InviteDTO receiver, Principal connectedUser, String email);

   // void createInvite(User user, InviteDTO receiver, String inviteToken, String email);
    GenResponse createInvite(InviteDTO isUser);

    List<Invite> getAllInvitations();

    void acceptInvite(String inviteCode);
    void declineInvite( String inviteCode);
}
