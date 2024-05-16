package com.titus.socialnetworkingsite2.repositories;

import com.titus.socialnetworkingsite2.Dto.Response.MessageContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ChatMessageRepository extends JpaRepository<MessageContent, String> {

}
