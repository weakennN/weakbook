package com.weakennN.weakbook.service;

import com.weakennN.weakbook.binding.CommentBinding;
import com.weakennN.weakbook.entity.Comment;
import com.weakennN.weakbook.entity.CommentLike;
import com.weakennN.weakbook.entity.Post;
import com.weakennN.weakbook.entity.User;
import com.weakennN.weakbook.repository.CommentLikeRepository;
import com.weakennN.weakbook.repository.CommentRepository;
import com.weakennN.weakbook.repository.PostRepository;
import com.weakennN.weakbook.repository.UserRepository;
import com.weakennN.weakbook.security.ApplicationUser;
import com.weakennN.weakbook.utils.ViewMapper;
import com.weakennN.weakbook.view.CommentView;
import com.weakennN.weakbook.view.LikeView;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private UserRepository userRepository;
    private CommentLikeRepository commentLikeRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository
            , UserRepository userRepository, CommentLikeRepository commentLikeRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentLikeRepository = commentLikeRepository;
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

        return ViewMapper.mapToCommentView(comment, post, user);
    }

    public List<CommentView> getReplies(Long commentId, int offset) {
        List<Comment> comments = this.commentRepository.getReplies(commentId, offset);
        List<CommentView> result = new ArrayList<>();

        for (Comment comment : comments) {
            result.add(ViewMapper.mapToCommentView(comment, this.postRepository.findByCommentId(commentId), this.commentRepository));
        }

        return result;
    }

    public List<CommentView> getPostComments(Long postId, int offset) {
        List<Comment> comments = this.commentRepository.getCommentsByPostId(postId, offset);
        Post post = this.postRepository.findById(postId).get();
        List<CommentView> result = new ArrayList<>();

        for (Comment comment : comments) {
            result.add(ViewMapper.mapToCommentView(comment, post, this.commentRepository));
        }

        return result;
    }

    public LikeView like(Long commentId) {
        Comment comment = this.commentRepository.findById(commentId).get();
        User user = this.userRepository.findById(AuthService.getUser().getId()).get();
        if (this.commentLikeRepository.existsByComment(comment)) {
            this.commentLikeRepository.delete(this.commentLikeRepository.findByCommentAndUser(comment, user));
            return new LikeView(false);
        }
        this.commentLikeRepository.save(new CommentLike(user, comment));
        return new LikeView(true);
    }
}
