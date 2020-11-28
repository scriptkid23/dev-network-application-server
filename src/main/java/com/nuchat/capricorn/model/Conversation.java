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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public void setCreated_at( Date created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public void setParticipants(Collection<Participants> participants) {
        this.participants = participants;
    }

    public void setMessages(Collection<Messages> messages) {
        this.messages = messages;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
