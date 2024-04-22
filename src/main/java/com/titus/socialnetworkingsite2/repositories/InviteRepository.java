package com.titus.socialnetworkingsite2.repositories;

import com.titus.socialnetworkingsite2.model.Invite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InviteRepository extends JpaRepository<Invite, Integer> {
   // InviteDTO findByEmail(InviteDTO receiver);
    Optional<Invite> findByRecipientEmail(String email);

}
