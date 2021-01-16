package com.nuchat.capricorn.dto;

public class StatusUser {
    private String from;
    private String text;
    private String recipient;
    private String time;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public StatusUser(String from, String text, String recipient) {
        this.from = from;
        this.text = text;
        this.recipient = recipient;
    }
}
