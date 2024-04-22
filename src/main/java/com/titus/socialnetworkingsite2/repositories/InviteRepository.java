package com.titus.socialnetworkingsite2.repositories;

import com.titus.socialnetworkingsite2.model.Invite;
import com.titus.socialnetworkingsite2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface InviteRepository extends JpaRepository<Invite, Integer> {
    //Object findByEmail(String receiver);
    //  Optional<User> findByEmail(String email);

}
