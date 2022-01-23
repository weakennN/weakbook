package com.weakennN.weakbook.repository;

import com.weakennN.weakbook.entity.Comment;
import com.weakennN.weakbook.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    int countCommentByPost(Post post);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO replies (comment_id, reply_id) VALUES (:commentId, :replyId)", nativeQuery = true)
    void addReply(@Param("commentId") Long commentId, @Param("replyId") Long replyId);
}
