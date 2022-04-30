package com.weakennN.weakbook.view;

import com.weakennN.weakbook.utils.hypermedia.Link;
import com.weakennN.weakbook.utils.hypermedia.RepresentationModel;

public class ChatRoomView extends RepresentationModel {

    private Long id;
    private String name;
    private String roomImage;
    private String latestMessage;

    public String getName() {
        return name;
    }

    public ChatRoomView() {
    }

    public ChatRoomView(Long id, String latestMessage) {
        this.id = id;
        this.latestMessage = latestMessage;
    }

    public ChatRoomView setName(String name) {
        this.name = name;
        return this;
    }

    public String getRoomImage() {
        return roomImage;
    }

    public void setRoomImage(String roomImage) {
        this.roomImage = roomImage;
    }

    public String getLatestMessage() {
        return latestMessage;
    }

    public void setLatestMessage(String latestMessage) {
        this.latestMessage = latestMessage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public void initLinks() {
        super.addLink("messages", new Link("/chat/chatRooms/messages/" + this.id));
        super.addLink("connect", new Link("/chat"));
        super.addLink("subscribe", new Link("/user/queue/chat"));
    }
}
