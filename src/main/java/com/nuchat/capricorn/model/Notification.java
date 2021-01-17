package com.nuchat.capricorn.model;

import javax.persistence.*;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="user_id",nullable = false)
    private User user;

    private boolean is_confirm;
    private String sender_from;

    public Notification() { }

    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isIs_confirm() {
        return is_confirm;
    }

    public void setIs_confirm(boolean is_confirm) {
        this.is_confirm = is_confirm;
    }

    public User getUser() {
        return user;
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


    public Notification(User user, boolean is_confirm, String sender_from, String content) {
        this.user = user;
        this.is_confirm = is_confirm;
        this.sender_from = sender_from;
        this.content = content;
    }
}
