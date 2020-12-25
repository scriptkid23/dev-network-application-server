package com.nuchat.capricorn.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;

@Entity
public class Messages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String guid;

    @Enumerated(EnumType.ORDINAL)
    private MessageType message_type;

    private String message;
    private Date created_at;


    @OneToMany(fetch = FetchType.LAZY,mappedBy = "messages",cascade = CascadeType.ALL)
    private Collection<Attachments> attachments;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public MessageType getMessage_type() {
        return message_type;
    }

    public void setMessage_type(MessageType message_type) {
        this.message_type = message_type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Collection<Attachments> getAttachments() {
        return attachments;
    }

    public void setAttachments(Collection<Attachments> attachments) {
        this.attachments = attachments;
    }



    public void setUser(User user) {
        this.user = user;
    }


    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

}
