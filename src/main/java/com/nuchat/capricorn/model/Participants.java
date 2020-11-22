package com.nuchat.capricorn.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
public class Participants {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(columnDefinition = "ENUM('SINGLE','GROUP')")
    @Enumerated(EnumType.STRING)
    private ParticipantType type;

    private Timestamp created_at;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;
}
