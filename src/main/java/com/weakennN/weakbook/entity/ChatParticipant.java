package com.weakennN.weakbook.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "chat_participants")
public class ChatParticipant extends BaseEntity {

    @ManyToOne
    private ChatRoom chatRoom;
    @ManyToOne
    private User user;

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public ChatParticipant setChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
        return this;
    }

    public User getUser() {
        return user;
    }

    public ChatParticipant setUser(User user) {
        this.user = user;
        return this;
    }
}
