package com.weakennN.weakbook.view;

public class Notification {

    private String message;
    private String link;

    public Notification(String message, String link) {
        this.message = message;
        this.link = link;
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
}
