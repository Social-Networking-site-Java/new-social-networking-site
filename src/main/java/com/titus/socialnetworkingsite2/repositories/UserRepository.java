package com.titus.socialnetworkingsite2.repositories;


import com.titus.socialnetworkingsite2.model.Status;
import com.titus.socialnetworkingsite2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String userName);

  //  List<User> findAllByStatus(Status status);

   // User findByFirstname (String userId);

   // Optional<User> findByUsername(String firstname);
}
