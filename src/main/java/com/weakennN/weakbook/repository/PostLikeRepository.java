package com.weakennN.weakbook.repository;

import com.weakennN.weakbook.entity.Post;
import com.weakennN.weakbook.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    int countPostLikeByPost(Post post);

    @Query(value = "select * from post_likes where post_id = :postId and user_id = :userId limit 1", nativeQuery = true)
    PostLike findByPostAndUser(@Param("postId") Long postId, @Param("userId") Long userId);

    @Query(value = "select * from post_likes where post_id = ?1 limit 10 offset ?2", nativeQuery = true)
    List<PostLike> getPostLikes(Long postId, int passedLikes);
}
