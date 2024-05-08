package com.titus.socialnetworkingsite2.repositories;

import com.titus.socialnetworkingsite2.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

}
