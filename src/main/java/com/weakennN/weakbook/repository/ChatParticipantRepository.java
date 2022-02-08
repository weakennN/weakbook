package com.weakennN.weakbook.repository;

import com.weakennN.weakbook.entity.ChatParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatParticipantRepository extends JpaRepository<ChatParticipant, Long> {
}
