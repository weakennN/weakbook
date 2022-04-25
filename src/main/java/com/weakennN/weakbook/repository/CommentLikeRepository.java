package com.weakennN.weakbook.repository;

import com.weakennN.weakbook.entity.Comment;
import com.weakennN.weakbook.entity.CommentLike;
import com.weakennN.weakbook.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    boolean existsByComment(Comment comment);

    CommentLike findByCommentAndUser(Comment comment, User user);
}
