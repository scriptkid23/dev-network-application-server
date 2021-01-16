package com.nuchat.capricorn.dto;

public class NotificationWebSocketResponse {
    private boolean is_confirm;
    private String sender_from;
    private String content;

    public boolean isIs_confirm() {
        return is_confirm;
    }

    public void setIs_confirm(boolean is_confirm) {
        this.is_confirm = is_confirm;
    }

    public String getSender_from() {
        return sender_from;
    }

    public void setSender_from(String sender_from) {
        this.sender_from = sender_from;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
