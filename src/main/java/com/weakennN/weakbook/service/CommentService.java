package com.weakennN.weakbook.service;

import com.weakennN.weakbook.entity.Post;
import com.weakennN.weakbook.repository.CommentRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public int getCountComments(Post post) {
        return this.commentRepository.countCommentByPost(post);
    }
}
