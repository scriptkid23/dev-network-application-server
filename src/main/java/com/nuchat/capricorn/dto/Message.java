package com.nuchat.capricorn.dto;

public class Message {
    private String username;
    private String message;
    private String roomid;

    public Message(String username, String message, String roomid) {
        this.username = username;
        this.message = message;
        this.roomid = roomid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }

    public String getRoomid() {
        return roomid;
    }
}
