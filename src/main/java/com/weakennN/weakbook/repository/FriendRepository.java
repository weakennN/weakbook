package com.weakennN.weakbook.repository;

import com.weakennN.weakbook.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    @Query(value = "select *\n" +
            "            from friends f\n" +
            "                   inner join users u on f.owner_id = u.id or f.user_id = u.id\n" +
            "            where u.id != ?1\n" +
            "             and f.is_blocked = false\n" +
            "             and (f.user_id = ?1\n" +
            "              or f.owner_id = ?1) limit ?2 offset ?3", nativeQuery = true)
    List<Friend> getFriendsByUserId(Long userId, int limit, int offset);

    @Query(value = "select count(id) from friends where owner_id = ?1 or user_id = ?1", nativeQuery = true)
    int countByUserId(Long userId);

    @Modifying
    @Transactional
    @Query(value = "insert into `friends` (owner_id, user_id, is_blocked) values (1?, 2?, false)", nativeQuery = true)
    void insert(Long ownerId, Long userId);

    @Query(value = "select * from friends where owner_id = ?1 and user_id = ?2 or owner_id = ?2 and user_id = ?1", nativeQuery = true)
    Friend areFriends(Long userId, Long friendId);

    @Modifying
    @Transactional
    @Query(value = "delete * from friends where owner_id = ?1 and user_id = ?2 or owner_id = ?2 and user_id = ?1", nativeQuery = true)
    void removeFriend(Long friendId, Long userId);
}
