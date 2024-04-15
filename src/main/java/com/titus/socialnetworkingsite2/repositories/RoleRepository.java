package com.titus.socialnetworkingsite2.repositories;


import com.titus.socialnetworkingsite2.Dto.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(String role);
}
