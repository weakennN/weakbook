package com.weakennN.weakbook.repository;

import com.weakennN.weakbook.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Modifying
    @Transactional
    @Query(value = "insert into notifications (notification_type, entity_id ,sender_id, receiver_id, seen) values(?1, ?2, ?3, ?4, false)", nativeQuery = true)
    void insert(int notificationType, Long entityId, Long senderId, Long receiverId);
}
