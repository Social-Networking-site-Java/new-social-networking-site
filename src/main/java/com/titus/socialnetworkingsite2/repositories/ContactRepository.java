package com.titus.socialnetworkingsite2.repositories;

import com.titus.socialnetworkingsite2.model.Contacts;
import com.titus.socialnetworkingsite2.model.Invite;
import com.titus.socialnetworkingsite2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContactRepository extends JpaRepository<Contacts, String> {

}
