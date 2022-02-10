package com.weakennN.weakbook.repository;

import com.weakennN.weakbook.entity.ChatParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatParticipantRepository extends JpaRepository<ChatParticipant, Long> {

    @Query(value = "SELECT email FROM chat_participants cp JOIN users u ON cp.user_id = u.id WHERE u.id != :userId AND cp.chat_room_id = :chatRoomId"
            , nativeQuery = true)
    List<String> findParticipantsEmailByChatRoom(@Param("userId") Long userId, @Param("chatRoomId") Long chatRoomId);
}
