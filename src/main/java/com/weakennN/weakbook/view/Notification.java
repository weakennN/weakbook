package com.weakennN.weakbook.view;

import com.weakennN.weakbook.entity.NotificationType;

public class Notification {

    private String message;
    private String link;
    private NotificationType type;

    public Notification(String message, String link, NotificationType notificationType) {
        this.message = message;
        this.link = link;
        this.type = notificationType;
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
}
