package com.titus.socialnetworkingsite2.services;

import com.titus.socialnetworkingsite2.Dto.BlackListDTO;
import com.titus.socialnetworkingsite2.Dto.Response.GenResponse;
import com.titus.socialnetworkingsite2.Dto.InviteDTO;
import com.titus.socialnetworkingsite2.model.Invite;
import com.titus.socialnetworkingsite2.model.User;

import java.security.Principal;
import java.util.List;

public interface UserInvitationService {


    GenResponse createInvite(InviteDTO isUser, BlackListDTO blackListDTO, Principal principal);

    List<String> getAllInvitations();

    void acceptInvite(String inviteCode);
    void declineInvite( String inviteCode);
}
