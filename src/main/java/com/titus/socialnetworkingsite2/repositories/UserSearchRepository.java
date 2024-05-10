package com.titus.socialnetworkingsite2.repositories;

import com.titus.socialnetworkingsite2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserSearchRepository extends JpaRepository<User, Integer> {
    List<User> findByEmailContainingOrUsernameContaining(String searchTerm, String searchTerm1);
}
