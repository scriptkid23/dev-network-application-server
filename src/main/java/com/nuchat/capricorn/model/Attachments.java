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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setThumb_url(String thumb_url) {
        this.thumb_url = thumb_url;
    }

    public void setFile_url(String file_url) {
        this.file_url = file_url;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public void setMessages(Messages messages) {
        this.messages = messages;
    }
}
