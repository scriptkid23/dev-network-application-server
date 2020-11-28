package com.nuchat.capricorn.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
public class Messages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String guid;

    @Enumerated(EnumType.ORDINAL)
    private MessageType message_type;

    private String message;
    private Timestamp created_at;


    @OneToMany(fetch = FetchType.LAZY,mappedBy = "messages",cascade = CascadeType.ALL)
    private Collection<Attachments> attachments;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public void setMessage_type(MessageType message_type) {
        this.message_type = message_type;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setAttachments(Collection<Attachments> attachments) {
        this.attachments = attachments;
    }
}
