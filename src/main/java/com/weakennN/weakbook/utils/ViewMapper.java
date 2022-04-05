package com.weakennN.weakbook.utils;

import com.weakennN.weakbook.binding.CommentBinding;
import com.weakennN.weakbook.entity.*;
import com.weakennN.weakbook.repository.ChatMessagesRepository;
import com.weakennN.weakbook.repository.ChatParticipantRepository;
import com.weakennN.weakbook.repository.CommentRepository;
import com.weakennN.weakbook.repository.PostLikeRepository;
import com.weakennN.weakbook.service.AuthService;
import com.weakennN.weakbook.service.DropBoxService;
import com.weakennN.weakbook.view.*;
import org.modelmapper.ModelMapper;

import java.util.List;

public class ViewMapper {

    private static final ModelMapper mapper = new ModelMapper();

    public static PostView mapToPostView(Post post, CommentRepository commentRepository
            , PostLikeRepository postLikeRepository, User user, DropBoxService dropBoxService) {
        PostView postView = mapper.map(post, PostView.class);
        postView
                .setId(post.getId())
                .setContent(post.getContent())
                .setNumberComments(commentRepository.countCommentByPost(post))
                .setNumberLikes(postLikeRepository.countPostLikeByPost(post))
                .setUser(mapper.map(user, UserView.class))
                .setIsLiked(postLikeRepository.countPostLikeByPost(post) >= 1);
        postView.getUser().setProfilePicture(user.getProfilePicture());

        for (PostPicture postPicture : post.getPictures()) {
            postView.addImageUrl(dropBoxService.getImageUrl(postPicture.getPath()));
        }

        for (Comment comment : commentRepository.getCommentsByPostId(post.getId(), 0)) {
            postView.addComment(mapToCommentView(comment, post, commentRepository));
        }

        postView.initSelfLinks();
        return postView;
    }

    public static CommentView mapToCommentView(CommentBinding commentBinding, Post post, User user) {
        CommentView commentView = new CommentView();
        commentView.
                setComment(commentBinding.getComment())
                .setUser(mapper.map(user, UserView.class))
                .setPostId(post.getId());

        return commentView;
    }

    public static CommentView mapToCommentView(Comment comment, Post post, CommentRepository commentRepository) {
        CommentView commentView = new CommentView();
        Long countReplies = commentRepository.getCountReplies(comment.getId());
        commentView
                .setComment(comment.getComment())
                .setUser(mapper.map(comment.getUser(), UserView.class))
                .setCountReplies(countReplies)
                .setId(comment.getId())
                .setPostId(post.getId());

        if (countReplies > 0) {
            commentView.setHasMoreReplies(true);
        }

        return commentView;
    }

    public static ChatRoomView mapToChatRoomView(ChatRoom chatRoom
            , ChatMessagesRepository chatMessagesRepository
            , ChatParticipantRepository chatParticipantRepository) {
        ChatMessage chatMessage = chatMessagesRepository.getLatestChatMessage(chatRoom.getId());
        String latestMessage = chatMessage == null ? "Message" : chatMessage.getMessage();
        ChatRoomView resultChatRoom = new ChatRoomView(chatRoom.getId(), latestMessage);
        List<ChatParticipant> chatParticipants = chatParticipantRepository.findAllByChatRoom(chatRoom);

        if (chatParticipants.size() == 2) {
            if (chatParticipants.get(0).getUser().getId().equals(AuthService.getUser().getId())) {
                resultChatRoom.setRoomImage(chatParticipants.get(0).getUser().getProfilePicture());
                resultChatRoom.setName(chatParticipants.get(1).getUser().getFirstName()
                        + " " + chatParticipants.get(1).getUser().getLastName());
            } else {
                resultChatRoom.setRoomImage(chatParticipants.get(1).getUser().getProfilePicture());
                resultChatRoom.setName(chatParticipants.get(0).getUser().getFirstName()
                        + " " + chatParticipants.get(0).getUser().getLastName());
            }
        }

        return resultChatRoom;
    }

    public static Message mapMessage(ChatMessage chatMessage, User user) {
        Message message = mapper.map(chatMessage, Message.class);
        message.setUser(mapper.map(user, UserView.class));
        return message;
    }

    public static UserView mapUser(User user) {
        return mapper.map(user, UserView.class);
    }

    public static UserProfileView mapToUserProfile(User user) {
        return mapper.map(user, UserProfileView.class);
    }
}
