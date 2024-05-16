package com.titus.socialnetworkingsite2.services;

import com.titus.socialnetworkingsite2.Dto.Response.GenResponse;
import com.titus.socialnetworkingsite2.Dto.InviteDTO;
import com.titus.socialnetworkingsite2.model.Invite;

import java.util.List;

public interface UserInvitationService {

    GenResponse createInvite(InviteDTO isUser);

    List<Invite> getAllReceivedInvitation(String sender);

    List<Invite> getAllAcceptedInvitations(String sender);

    GenResponse acceptInvite(String inviteCode);
    GenResponse declineInvite( String inviteCode);
    List<Invite> getAllInvitations();
}
