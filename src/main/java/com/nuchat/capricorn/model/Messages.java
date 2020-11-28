package com.nuchat.capricorn.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
public class Messages {
    @Id
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

}
