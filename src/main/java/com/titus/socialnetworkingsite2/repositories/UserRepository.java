package com.titus.socialnetworkingsite2.repositories;


import com.titus.socialnetworkingsite2.model.Status;
import com.titus.socialnetworkingsite2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);

    List<User> findAllByStatus(Status status);
}
