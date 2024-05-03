package com.titus.socialnetworkingsite2.repositories;

import com.titus.socialnetworkingsite2.model.BlackList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlackListRepository extends JpaRepository<BlackList, Integer> {

    BlackList findByBlacklisted(String blacklisted);

    @Query("SELECT b.blacklisted FROM BlackList b")
    List<String> findAllBlacklisted();

    boolean existsByBlacklistedAndBlacklistedBy(String blacklisted, String blacklistedBy);



}
