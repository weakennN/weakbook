package com.weakennN.weakbook.repository;

import com.weakennN.weakbook.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    @Query(value = "select *\n" +
            "from friends f\n" +
            "         inner join users u on f.owner_id = u.id or f.user_id = u.id\n" +
            "where u.id != ?1\n" +
            "  and f.is_blocked = false\n" +
            "  and (f.user_id = ?1\n" +
            "    or f.owner_id = ?1);", nativeQuery = true)
    List<Friend> getFriendsByUserId(Long userId, int limit);

    @Query(value = "select count(id) from friends where owner_id = ?1 or user_id = ?1", nativeQuery = true)
    int countByUserId(Long userId);
}
