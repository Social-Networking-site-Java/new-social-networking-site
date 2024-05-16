package com.titus.socialnetworkingsite2.repositories;

import com.titus.socialnetworkingsite2.Dto.InviteDTO;
import com.titus.socialnetworkingsite2.model.Invite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface InviteRepository extends JpaRepository<Invite, Integer> {

    Optional<Invite> findByInviteCode(String user);

    List<Invite> findAllBySender(String sender);

    List<Invite> findByAcceptedTrueAndSender(String sender);

    Optional<Invite> findByRecipientAndSenderIgnoreCase(String recipientEmail, String sender);

}
