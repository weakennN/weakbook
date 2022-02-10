package com.weakennN.weakbook.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "chat_messages")
public class ChatMessage extends BaseEntity {

    private String message;
    @ManyToOne
    private User user;
    @ManyToOne
    private ChatRoom chatRoom;

    public ChatMessage(String message, User user, ChatRoom chatRoom) {
        this.message = message;
        this.user = user;
        this.chatRoom = chatRoom;
    }

    public ChatMessage() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
    }
}
