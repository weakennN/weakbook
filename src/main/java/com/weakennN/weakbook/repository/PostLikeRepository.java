package com.weakennN.weakbook.repository;

import com.weakennN.weakbook.entity.Post;
import com.weakennN.weakbook.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    int countPostLikeByPost(Post post);
}
