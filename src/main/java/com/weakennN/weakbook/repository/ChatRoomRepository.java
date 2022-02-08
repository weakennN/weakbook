package com.weakennN.weakbook.repository;

import com.weakennN.weakbook.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query(value = "SELECT * FROM chat_rooms c JOIN chat_participants cp ON c.id = cp.chat_room_id WHERE user_id = ?1"
            , nativeQuery = true)
    List<ChatRoom> getAllByUserId(Long userId);
}
