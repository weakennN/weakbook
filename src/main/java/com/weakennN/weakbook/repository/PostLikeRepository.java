package com.weakennN.weakbook.repository;

import com.weakennN.weakbook.entity.Post;
import com.weakennN.weakbook.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    int countPostLikeByPost(Post post);

    @Query(value = "Select * from post_likes where post_id = :postId and user_id = :userId limit 1", nativeQuery = true)
    PostLike findByPostAndUser(@Param("postId") Long postId, @Param("userId") Long userId);
}
