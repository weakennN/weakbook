package com.weakennN.weakbook.repository;

import com.weakennN.weakbook.entity.Comment;
import com.weakennN.weakbook.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "select count(c.id) from comments c where post_id = ?1 and c.id not in (select r.reply_id from replies r)", nativeQuery = true)
    int countCommentByPostId(Long id);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO replies (comment_id, reply_id) VALUES (:commentId, :replyId)", nativeQuery = true)
    void addReply(@Param("commentId") Long commentId, @Param("replyId") Long replyId);

    @Query(value = "SELECT * FROM `comments` c WHERE `post_id` = :postId AND c.id NOT IN (SELECT reply_id FROM replies) LIMIT 10 OFFSET :offset", nativeQuery = true)
    List<Comment> getCommentsByPostId(@Param("postId") Long postId, @Param("offset") int offset);

    @Query(value = "SELECT * FROM comments e JOIN replies r ON e.id = r.reply_id WHERE r.comment_id = ?1 LIMIT 5 OFFSET ?2", nativeQuery = true)
    List<Comment> getReplies(Long commentId, int offset);

    @Query(value = "SELECT COUNT(comment_id) FROM replies WHERE comment_id = ?1", nativeQuery = true)
    Long getCountReplies(Long commentId);
}
