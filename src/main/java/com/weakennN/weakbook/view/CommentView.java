package com.weakennN.weakbook.view;

import java.util.ArrayList;
import java.util.List;

public class CommentView {

    private Long id;
    private Long postId;
    private UserView user;
    private String comment;
    private List<CommentView> replies = new ArrayList<>();
    private List<String> pictureUrls = new ArrayList<>();
    private boolean hasMoreReplies;
    private Long countReplies;

    public UserView getUser() {
        return user;
    }

    public CommentView setUser(UserView user) {
        this.user = user;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public CommentView setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public List<CommentView> getReplies() {
        return replies;
    }

    public CommentView setReplies(List<CommentView> replies) {
        this.replies = replies;
        return this;
    }

    public void addPictureUrl(String pictureUrl) {
        this.pictureUrls.add(pictureUrl);
    }

    public void addReply(CommentView reply) {
        this.replies.add(reply);
    }

    public CommentView setCountReplies(Long countReplies) {
        this.countReplies = countReplies;
        return this;
    }

    public Long getCountReplies() {
        return countReplies;
    }

    public CommentView setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getId() {
        return this.id;
    }

    public List<String> getPictureUrls() {
        return pictureUrls;
    }

    public void setPictureUrls(List<String> pictureUrls) {
        this.pictureUrls = pictureUrls;
    }

    public boolean isHasMoreReplies() {
        return hasMoreReplies;
    }

    public CommentView setHasMoreReplies(boolean hasMoreReplies) {
        this.hasMoreReplies = hasMoreReplies;
        return this;
    }

    public Long getPostId() {
        return postId;
    }

    public CommentView setPostId(Long postId) {
        this.postId = postId;
        return this;
    }
}
