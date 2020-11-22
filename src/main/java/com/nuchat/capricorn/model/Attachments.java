package com.nuchat.capricorn.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@Entity
public class Attachments {
    @Id
    private Integer id;

    private String thumb_url;
    private String file_url;
    private Timestamp created_at;

    @ManyToOne
    @JoinColumn(name="message_id")
    private Messages messages;
}
