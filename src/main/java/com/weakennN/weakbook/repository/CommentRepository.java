package com.weakennN.weakbook.repository;

import com.weakennN.weakbook.entity.Comment;
import com.weakennN.weakbook.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    int countCommentByPost(Post post);
}
