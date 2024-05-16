package com.titus.socialnetworkingsite2.repositories;

import com.titus.socialnetworkingsite2.Dto.BlackListDTO;
import com.titus.socialnetworkingsite2.model.BlackList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BlackListRepository extends JpaRepository<BlackList, Integer> {

    BlackList findByBlacklisted(String blacklisted);

    List<BlackList> findAllByBlacklistedBy(String username);

    Optional<BlackList> findByBlacklistedAndBlacklistedBy(String blacklisted, String blacklistedBy);



}
