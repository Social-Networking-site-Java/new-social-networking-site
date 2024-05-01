package com.titus.socialnetworkingsite2.repositories;

import com.titus.socialnetworkingsite2.model.BlackList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlackListRepository extends JpaRepository<BlackList, Integer> {
  //  BlackList findByUserAndBlacklistedUser(User user, User blacklistedUser);

  //  boolean existsByUserAndBlacklistedUser(User user, User blackListUser);

   // Optional<BlackList> findByBlacklisted(String blacklisted);

    List<BlackList> findAllById(Integer id);

    BlackList findByBlacklisted(String blacklisted);

    boolean existsByBlacklistedAndBlacklistedBy(String blacklisted, String blacklistedBy);

    BlackList findByBlacklistedAndBlacklistedBy(String blacklisted, String blacklistedBy);

    //BlackListDTO findByIsBlacklist(Integer isBlacklist);

}
