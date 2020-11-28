package com.nuchat.capricorn.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Attachments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String thumb_url;
    private String file_url;
    private Timestamp created_at;

    @ManyToOne
    @JoinColumn(name="message_id")
    private Messages messages;
}
