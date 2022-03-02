package com.weakennN.weakbook.view;

public class PostLikeView {

    private boolean isLiked;

    public PostLikeView(boolean isLiked) {
        this.isLiked = isLiked;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean isLiked) {
        this.isLiked = isLiked;
    }
}
