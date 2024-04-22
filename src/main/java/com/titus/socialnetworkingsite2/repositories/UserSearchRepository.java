package com.titus.socialnetworkingsite2.repositories;

import com.titus.socialnetworkingsite2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSearchRepository extends JpaRepository<User, Integer> {
    List<User> findByEmailContainingOrFirstnameContaining(String searchTerm, String searchTerm1);
}
