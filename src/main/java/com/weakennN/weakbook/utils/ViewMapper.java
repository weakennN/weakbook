package com.weakennN.weakbook.utils;

import com.weakennN.weakbook.entity.*;
import com.weakennN.weakbook.repository.ChatMessagesRepository;
import com.weakennN.weakbook.repository.ChatParticipantRepository;
import com.weakennN.weakbook.repository.CommentRepository;
import com.weakennN.weakbook.repository.PostLikeRepository;
import com.weakennN.weakbook.service.AuthService;
import com.weakennN.weakbook.service.DropBoxService;
import com.weakennN.weakbook.view.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ViewMapper {

    private static final ModelMapper mapper = new ModelMapper();
    private static final DropBoxService dropBoxService = new DropBoxService();

    public static PostView mapToPostView(Post post, CommentRepository commentRepository
            , PostLikeRepository postLikeRepository, User user) {
        PostView postView = mapper.map(post, PostView.class);
        postView
                .setId(post.getId())
                .setContent(post.getContent())
                .setNumberComments(commentRepository.countCommentByPostId(post.getId()))
                .setNumberLikes(postLikeRepository.countPostLikeByPost(post))
                .setUser(mapper.map(user, UserView.class))
                .setIsLiked(postLikeRepository.isLiked(AuthService.getUser().getId(), post.getId()) != null)
                .getUser().setProfilePicture(dropBoxService.getImageUrl(user.getProfilePicture()));

        for (PostPicture postPicture : post.getPictures()) {
            postView.addImageUrl(dropBoxService.getImageUrl(postPicture.getPath()));
        }

        for (Comment comment : commentRepository.getCommentsByPostId(post.getId(), 0)) {
            postView.addComment(mapToCommentView(comment, post, commentRepository));
        }

        postView.initLinks();
        return postView;
    }

    public static CommentView mapToCommentView(Comment comment, Post post, User user) {
        CommentView commentView = new CommentView();
        commentView
                .setId(comment.getId())
                .setComment(comment.getComment())
                .setUser(mapper.map(user, UserView.class))
                .setPostId(post.getId());
        commentView.getUser().setProfilePicture(dropBoxService.getImageUrl(commentView.getUser().getProfilePicture()));
        commentView.initLinks();

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
        commentView.getUser().setProfilePicture(dropBoxService.getImageUrl(commentView.getUser().getProfilePicture()));
        if (countReplies > 10)
            commentView.setHasMoreReplies(true);
        commentView.initLinks();

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
                resultChatRoom.setRoomImage(dropBoxService.getImageUrl(chatParticipants.get(1).getUser().getProfilePicture()));
                resultChatRoom.setName(chatParticipants.get(1).getUser().getFirstName()
                        + " " + chatParticipants.get(1).getUser().getLastName());
            } else {
                resultChatRoom.setRoomImage(dropBoxService.getImageUrl(chatParticipants.get(0).getUser().getProfilePicture()));
                resultChatRoom.setName(chatParticipants.get(0).getUser().getFirstName()
                        + " " + chatParticipants.get(0).getUser().getLastName());
            }
        }
        resultChatRoom.initLinks();

        return resultChatRoom;
    }

    public static Message mapMessage(ChatMessage chatMessage, User user) {
        Message message = mapper.map(chatMessage, Message.class);
        message.setUser(mapUser(user));
        message.getUser().setProfilePicture(dropBoxService.getImageUrl(message.getUser().getProfilePicture()));
        return message;
    }

    public static UserView mapUser(User user) {
        UserView userView = mapper.map(user, UserView.class);
        userView.setProfilePicture(dropBoxService.getImageUrl(userView.getProfilePicture()));
        userView.initLinks();
        return userView;
    }

    public static UserProfileView mapToUserProfile(User user) {
        UserProfileView userProfileView = mapper.map(user, UserProfileView.class);
        userProfileView.setProfilePicture(dropBoxService.getImageUrl(user.getProfilePicture()));
        return userProfileView;
    }
}
