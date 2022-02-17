package com.weakennN.weakbook.view;

public class Message {

    private String message;
    private Long chatRoomId;
    private UserView user;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(Long chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public UserView getUserId() {
        return this.user;
    }

    public void setUserId(UserView user) {
        this.user = user;
    }
}
