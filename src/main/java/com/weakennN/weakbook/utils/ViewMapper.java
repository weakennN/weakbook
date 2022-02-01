package com.weakennN.weakbook.utils;

import com.weakennN.weakbook.binding.CommentBinding;
import com.weakennN.weakbook.entity.Comment;
import com.weakennN.weakbook.entity.Post;
import com.weakennN.weakbook.entity.PostPicture;
import com.weakennN.weakbook.entity.User;
import com.weakennN.weakbook.repository.CommentRepository;
import com.weakennN.weakbook.repository.PostLikeRepository;
import com.weakennN.weakbook.service.DropBoxService;
import com.weakennN.weakbook.view.CommentView;
import com.weakennN.weakbook.view.PostView;
import com.weakennN.weakbook.view.UserView;
import org.modelmapper.ModelMapper;

public class ViewMapper {

    private static ModelMapper mapper = new ModelMapper();

    public static PostView mapToPostView(Post post, CommentRepository commentRepository
            , PostLikeRepository postLikeRepository, User user, DropBoxService dropBoxService) {
        PostView postView = mapper.map(post, PostView.class);
        postView
                .setId(post.getId())
                .setContent(post.getContent())
                .setNumberComments(commentRepository.countCommentByPost(post))
                .setNumberLikes(postLikeRepository.countPostLikeByPost(post))
                .setUser(mapper.map(user, UserView.class));
        postView.getUser().setProfilePictureUrl(user.getProfilePicture());

        for (PostPicture postPicture : post.getPictures()) {
            postView.addImageUrl(dropBoxService.getImageUrl(postPicture.getPath()));
        }

        for (Comment comment : commentRepository.getCommentsByPostId(post.getId())) {
            postView.addComment(mapToCommentView(comment, commentRepository));
        }

        return postView;
    }

    public static CommentView mapToCommentView(CommentBinding commentBinding, User user) {
        CommentView commentView = new CommentView();
        commentView.setComment(commentBinding.getComment());
        commentView.setUser(mapper.map(user, UserView.class));

        return commentView;
    }

    public static CommentView mapToCommentView(Comment comment, CommentRepository commentRepository) {
        CommentView commentView = new CommentView();
        Long countReplies = commentRepository.getCountReplies(comment.getId());
        commentView
                .setComment(comment.getComment())
                .setUser(mapper.map(comment.getUser(), UserView.class))
                .setCountReplies(countReplies)
                .setId(comment.getId());

        if (countReplies > 0) {
            commentView.setHasMoreReplies(true);
        }

     /*  for (Comment reply : commentRepository.getReplies(comment.getId())) {
            commentView.addReply(mapToCommentView(reply, commentRepository));
        }

      */

        return commentView;
    }
}
