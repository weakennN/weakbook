package com.weakennN.weakbook.view;

import java.util.ArrayList;
import java.util.List;

public class CommentView {

    private UserView user;
    private String comment;
    private List<CommentView> replies = new ArrayList<>();
    private List<String> pictureUrls = new ArrayList<>();

    public UserView getUser() {
        return user;
    }

    public void setUser(UserView user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<CommentView> getReplies() {
        return replies;
    }

    public void setReplies(List<CommentView> replies) {
        this.replies = replies;
    }

    public void addPictureUrl(String pictureUrl) {
        this.pictureUrls.add(pictureUrl);
    }

    public void addReply(CommentView reply) {
        this.replies.add(reply);
    }
}
