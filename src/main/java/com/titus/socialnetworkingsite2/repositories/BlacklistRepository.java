package com.titus.socialnetworkingsite2.repositories;

import com.titus.socialnetworkingsite2.model.BlackList;
import com.titus.socialnetworkingsite2.model.BlackListUser;
import com.titus.socialnetworkingsite2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlacklistRepository extends JpaRepository<BlackListUser, Integer> {
    BlackListUser findByBlacklistedByAndBlacklisted(String email, String blacklist);

}
