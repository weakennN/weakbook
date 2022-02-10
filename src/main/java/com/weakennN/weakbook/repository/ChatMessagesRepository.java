package com.weakennN.weakbook.repository;

import com.weakennN.weakbook.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessagesRepository extends JpaRepository<ChatMessage, Long> {
}
