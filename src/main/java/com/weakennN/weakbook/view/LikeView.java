package com.weakennN.weakbook.view;

public class LikeView {

    boolean isLiked;

    public LikeView(boolean isLiked) {
        this.isLiked = isLiked;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }
}
