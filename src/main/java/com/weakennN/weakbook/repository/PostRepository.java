package com.weakennN.weakbook.repository;

import com.weakennN.weakbook.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "SELECT *\n" +
            "FROM posts\n" +
            "where user_id in (select owner_id from friends where owner_id = :userId or friends.user_id = :userId)\n" +
            "   or user_id in (select friends.user_id from friends where owner_id = :userId or friends.user_id = :userId)\n" +
            "   or user_id in (select user_id from posts where user_id = :userId)\n" +
            "order by created desc\n" +
            "limit :limit offset :passedPosts", nativeQuery = true)
    List<Post> findAllByUser(@Param("userId") Long userId, @Param("passedPosts") int passedPosts, @Param("limit") int limit);

    @Query(value = "SELECT * FROM posts p JOIN comments c ON p.id = c.post_id where post_id = 16 LIMIT ?1", nativeQuery = true)
    Post findByCommentId(Long commentId);

    @Query(value = "select * from posts where user_id = ?1 order by created desc limit 7 offset ?2", nativeQuery = true)
    List<Post> getUserPosts(Long userId, int offset);
}
