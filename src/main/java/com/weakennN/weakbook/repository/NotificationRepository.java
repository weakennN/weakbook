package com.weakennN.weakbook.repository;

import com.weakennN.weakbook.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Modifying
    @Transactional
    @Query(value = "insert into notifications (notification_type, entity_id ,sender_id, receiver_id, seen) values (?1, ?2, ?3, ?4, false)", nativeQuery = true)
    void insert(int notificationType, Long entityId, Long senderId, Long receiverId);

    @Query(value = "select * from notifications where receiver_id = ?1 order by created desc", nativeQuery = true)
    List<Notification> findByReceiver(Long receiverId);

    @Modifying
    @Transactional
    @Query(value = "update notifications set seen = true where receiver_id = ?1", nativeQuery = true)
    void seeNotifications(Long receiverId);

    @Query(value = "select * from notifications where sender_id = ?1 and receiver_id = ?2 and notification_type = ?3 and entity_id = ?4", nativeQuery = true)
    Notification findBySenderIdAndReceiverIdAndTypeAndEntityId(Long senderId, Long receiverId, int notificationType, Long entityId);

    @Modifying
    @Transactional
    @Query(value = "delete from notifications where entity_id = ?1 and notification_type = ?2", nativeQuery = true)
    void deleteByEntityIdAndNotificationType(Long entityId, int notificationType);
}
