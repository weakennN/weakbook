package com.weakennN.weakbook.view;

import com.weakennN.weakbook.entity.NotificationType;

public class Notification {

    private String message;
    private String link;
    private NotificationType type;
    private UserView sender;
    private Long entityId;

    public Notification() {
    }

    public Notification(String message, String link, NotificationType notificationType, UserView sender, Long entityId) {
        this.message = message;
        this.link = link;
        this.type = notificationType;
        this.sender = sender;
        this.entityId = entityId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public UserView getSender() {
        return sender;
    }

    public void setSender(UserView sender) {
        this.sender = sender;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }
}
