package com.weakennN.weakbook.repository;

import com.weakennN.weakbook.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessagesRepository extends JpaRepository<ChatMessage, Long> {

    @Query(value = "SELECT * FROM chat_messages WHERE chat_room_id = :chatRoomId ORDER BY created DESC LIMIT 10 OFFSET :offset", nativeQuery = true)
    List<ChatMessage> getChatMessageByChatRoomId(@Param("chatRoomId") Long chatRoomId, @Param("offset") int offset);

    @Query(value = "SELECT * FROM chat_messages WHERE chat_room_id = ?1 ORDER BY created DESC LIMIT 1", nativeQuery = true)
    ChatMessage getLatestChatMessage(Long chatRoomId);
}
