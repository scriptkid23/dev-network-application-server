package com.nuchat.capricorn.dto;

public class AcceptFriendRequestDTO {
    private Integer id;
    private String content;
    private String sender_from;
    private boolean is_confirm;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender_from() {
        return sender_from;
    }

    public void setSender_from(String sender_from) {
        this.sender_from = sender_from;
    }

    public boolean isIs_confirm() {
        return is_confirm;
    }

    public void setIs_confirm(boolean is_confirm) {
        this.is_confirm = is_confirm;
    }
}
