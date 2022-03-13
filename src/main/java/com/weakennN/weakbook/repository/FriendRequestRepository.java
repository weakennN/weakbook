package com.weakennN.weakbook.repository;

import com.weakennN.weakbook.entity.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO friend_requests (accepted, receiver_id, sender_id) VALUES (false, :receiverId, :senderId)", nativeQuery = true)
    void sendFriendRequest(@Param("receiverId") Long receiverId, @Param("senderId") Long senderId);

    @Query(value = "DELETE FROM friend_requests where id = ?1", nativeQuery = true)
    void delete(Long id);

    @Query(value = "UPDATE friend_requests SET accepted = true where id = ?1", nativeQuery = true)
    void accept(Long id);
}
