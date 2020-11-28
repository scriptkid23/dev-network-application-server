package com.nuchat.capricorn.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Participants {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.ORDINAL)
    private ParticipantType type;

    private Date created_at;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setType(ParticipantType type) {
        this.type = type;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }
}
