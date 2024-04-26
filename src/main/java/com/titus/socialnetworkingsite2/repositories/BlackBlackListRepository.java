//package com.titus.socialnetworkingsite2.repositories;
//
//import com.titus.socialnetworkingsite2.model.BlackList;
//import com.titus.socialnetworkingsite2.model.User;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface BlackBlackListRepository extends JpaRepository<BlackList, Integer> {
//    BlackList findByUserAndBlacklistedUser(User user, User blacklistedUser);
//
//    boolean existsByUserAndBlacklistedUser(User user, User blackListUser);
//
//
//   // List<BlackList> findByBlacklistedUser(User blacklistedUser);
//   // List<BlackList> findAllByBlacklistedUser(User user);
//   // List<BlackList> findAllByBlacklistedUserId();
//   // List<BlackList> findAllByBlacklistedUser(Integer blacklistedUser);
//}
