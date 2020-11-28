package com.nuchat.capricorn.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

@Entity
public class Messages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String guid;


    @Column(columnDefinition = "ENUM('TEXT','IMAGE','VIDEO','AUDIO')")
    @Enumerated(EnumType.STRING)
    private MessageType message_type;

    private String message;
    private Timestamp created_at;

    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "messages",cascade = CascadeType.ALL)
    private Collection<Attachments> attachments;

}
