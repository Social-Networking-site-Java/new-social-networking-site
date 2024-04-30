package com.titus.socialnetworkingsite2.repositories;

import com.titus.socialnetworkingsite2.model.Invite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InviteRepository extends JpaRepository<Invite, Integer> {
  //  List<Invite> findByRecipientEmail(String user);
    Optional<Invite> findByInviteCode(String user);


    boolean existsByRecipientEmail (String recipientEmail);
}