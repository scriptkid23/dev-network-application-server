package com.nuchat.capricorn.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String title;
    private String channel_id;
    private Timestamp created_at;
    private Timestamp updated_at;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "conversation",cascade = CascadeType.ALL)
    private Collection<Participants> participants;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "conversation",cascade = CascadeType.ALL)
    private Collection<Messages> messages;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;




}
