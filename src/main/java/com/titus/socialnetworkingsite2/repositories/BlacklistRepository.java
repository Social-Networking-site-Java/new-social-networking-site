package com.titus.socialnetworkingsite2.repositories;

import com.titus.socialnetworkingsite2.Dto.BlackListUsers;
import com.titus.socialnetworkingsite2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlacklistRepository extends JpaRepository<User, Integer> {
   // BlackListUsers findByEmail(String email);
    // Define additional methods if needed
}
