package com.titus.socialnetworkingsite2.repositories;

import com.titus.socialnetworkingsite2.model.Invite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface InviteRepository extends JpaRepository<Invite, Integer> {
    Optional<Invite> findByInviteCode(String user);

    Invite findBySender(String sender);

    @Query("SELECT b.recipientEmail From Invite b")
    List<String> findAllRecipientEmails();

}
