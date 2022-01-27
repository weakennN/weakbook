package com.weakennN.weakbook.binding;

import java.util.ArrayList;
import java.util.List;

public class CommentBinding {

    private String comment;
    private Long replyTo;
    private Long postId;
    private List<String> pictureUrls = new ArrayList<>();

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(Long replyTo) {
        this.replyTo = replyTo;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public List<String> getPictureUrls() {
        return this.pictureUrls;
    }
}
