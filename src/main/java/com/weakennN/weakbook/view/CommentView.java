package com.weakennN.weakbook.view;

import java.util.List;

public class CommentView {

    private UserView user;
    private String comment;
    private List<CommentView> replies;

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
}
