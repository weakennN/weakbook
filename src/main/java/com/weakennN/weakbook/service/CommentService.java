package com.weakennN.weakbook.service;

import com.weakennN.weakbook.binding.CommentBinding;
import com.weakennN.weakbook.entity.Comment;
import com.weakennN.weakbook.entity.Post;
import com.weakennN.weakbook.entity.User;
import com.weakennN.weakbook.repository.CommentRepository;
import com.weakennN.weakbook.repository.PostRepository;
import com.weakennN.weakbook.repository.UserRepository;
import com.weakennN.weakbook.security.ApplicationUser;
import com.weakennN.weakbook.utils.ViewMapper;
import com.weakennN.weakbook.view.CommentView;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository
            , UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public int getCountComments(Post post) {
        return this.commentRepository.countCommentByPost(post);
    }

    public CommentView comment(CommentBinding commentBinding) {
        Post post = this.postRepository.findById(commentBinding.getPostId()).orElseThrow(IllegalArgumentException::new);
        ApplicationUser applicationUser = (ApplicationUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = this.userRepository.findByEmail(applicationUser.getEmail()).get();

        Comment comment = new Comment();
        comment.setComment(commentBinding.getComment());
        comment.setUser(user);
        comment.setPost(post);
        comment = this.commentRepository.save(comment);

        if (commentBinding.getReplyTo() != null) {
            Comment parentComment = this.commentRepository.findById(commentBinding.getReplyTo())
                    .orElseThrow(IllegalArgumentException::new);
            this.commentRepository.addReply(parentComment.getId(), comment.getId());
        }

        return ViewMapper.mapToCommentView(commentBinding, post, user);
    }

    public List<CommentView> getComments(Long commentId, int offset) {
        List<Comment> comments = this.commentRepository.getReplies(commentId, offset);
        List<CommentView> result = new ArrayList<>();

        for (Comment comment : comments) {
            result.add(ViewMapper.mapToCommentView(comment, this.postRepository.findByCommentId(commentId), this.commentRepository));
        }

        return result;
    }
}
