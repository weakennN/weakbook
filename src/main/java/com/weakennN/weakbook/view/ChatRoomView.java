package com.weakennN.weakbook.view;

public class ChatRoomView {

    private Long id;
    private String name;
    private String roomPicturePath;
    private String latestMessage;

    public String getName() {
        return name;
    }

    public ChatRoomView setName(String name) {
        this.name = name;
        return this;
    }

    public String getRoomPicturePath() {
        return roomPicturePath;
    }

    public ChatRoomView setRoomPicturePath(String roomPicturePath) {
        this.roomPicturePath = roomPicturePath;
        return this;
    }

    public String getLatestMessage() {
        return latestMessage;
    }

    public ChatRoomView setLatestMessage(String latestMessage) {
        this.latestMessage = latestMessage;
        return this;
    }

    public Long getId() {
        return id;
    }

    public ChatRoomView setId(Long id) {
        this.id = id;
        return this;
    }
}
