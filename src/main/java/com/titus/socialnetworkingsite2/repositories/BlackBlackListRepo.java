package com.titus.socialnetworkingsite2.repositories;

import com.titus.socialnetworkingsite2.model.BlackListList;
import com.titus.socialnetworkingsite2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BlackBlackListRepo extends JpaRepository<BlackListList, Integer> {

    BlackListList findByUserAndBlacklisted(User user, String blackList);

    boolean existsByUserAndBlacklisted(User user, String blackList);

  //  List<BlackListList> findByBlacklisted(String blackList);


    // boolean existsByUserAndBlacklistedUser(Integer id);
}
