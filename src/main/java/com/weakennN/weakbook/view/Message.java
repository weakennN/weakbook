package com.weakennN.weakbook.view;

public class Message {

    private String message;
    private Long chatRoomId;
    private UserView user;
    private boolean fromCurrentUser;
    private boolean infoMessage;

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

    public UserView getUser() {
        return user;
    }

    public void setUser(UserView user) {
        this.user = user;
    }

    public boolean isFromCurrentUser() {
        return fromCurrentUser;
    }

    public void setFromCurrentUser(boolean fromCurrentUser) {
        this.fromCurrentUser = fromCurrentUser;
    }
}
