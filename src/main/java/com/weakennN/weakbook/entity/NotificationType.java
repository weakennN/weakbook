package com.weakennN.weakbook.entity;

public enum NotificationType {
    POST_LIKE(0),
    FRIEND_REQUEST(1),
    COMMENT_LIKE(2);

    private final int id;

    NotificationType(int id) {
        this.id = id;
    }
}
