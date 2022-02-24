package com.weakennN.weakbook.view;

public class ChatRoomView {

    private Long id;
    private String name;
    private String roomImage;
    private String latestMessage;

    public String getName() {
        return name;
    }

    public ChatRoomView(Long id, String latestMessage) {
        this.id = id;
        this.latestMessage = latestMessage;
    }

    public ChatRoomView() {

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
}
