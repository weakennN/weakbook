package com.weakennN.weakbook.repository;

import com.weakennN.weakbook.entity.Post;
import com.weakennN.weakbook.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "SELECT *\n" +
            "FROM posts\n" +
            "where user_id in (select owner_id from friends where owner_id = ?1 or friends.user_id = ?1)\n" +
            "   or user_id in (select friends.user_id from friends where owner_id = ?1 or friends.user_id = ?1)\n" +
            "order by created desc;", nativeQuery = true)
    List<Post> findAllByUser(Long userId);
}
