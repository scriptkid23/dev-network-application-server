package com.nuchat.capricorn.dto;

public class NotificationWebSocketDTO {
    private String sender;
    private String email;
    private String invitation_message;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInvitation_message() {
        return invitation_message;
    }

    public void setInvitation_message(String invitation_message) {
        this.invitation_message = invitation_message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public NotificationWebSocketDTO(String sender, String email, String invitation_message) {
        this.sender = sender;
        this.email = email;
        this.invitation_message = invitation_message;
    }
}
