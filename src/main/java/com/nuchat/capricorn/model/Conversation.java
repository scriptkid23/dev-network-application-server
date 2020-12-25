package com.nuchat.capricorn.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String channel_id;
    private Date created_at;
    private Date updated_at;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "conversation",cascade = CascadeType.ALL)
    private Collection<Participants> participants;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "conversation",cascade = CascadeType.ALL)
    private Collection<Messages> messages;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Conversation(){}
    public Conversation(String title, String channel_id, Date created_at, User user) {
        this.title = title;
        this.channel_id = channel_id;
        this.created_at = created_at;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public Collection<Participants> getParticipants() {
        return participants;
    }

    public void setParticipants(Collection<Participants> participants) {
        this.participants = participants;
    }

    public Collection<Messages> getMessages() {
        return messages;
    }

    public void setMessages(Collection<Messages> messages) {
        this.messages = messages;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
