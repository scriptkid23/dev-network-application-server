package com.nuchat.capricorn.dto;

import com.nuchat.capricorn.model.Messages;
import com.nuchat.capricorn.model.Participants;

import java.util.Collection;
import java.util.Date;

public class ConversationDTO {
    private Integer id;

    private String title;
    private String channel_id;
    private Date created_at;
    private Date updated_at;

    private Collection<Participants> participants;
    private Collection<Messages> messages;

    public ConversationDTO(Integer id, String title, String channel_id, Date created_at, Date updated_at, Collection<Participants> participants, Collection<Messages> messages) {
        this.id = id;
        this.title = title;
        this.channel_id = channel_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.participants = participants;
        this.messages = messages;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public Collection<Participants> getParticipants() {
        return participants;
    }

    public void setParticipants(Collection<Participants> participants) {
        this.participants = participants;
    }

    public Collection<Messages> getMessages() {
        return messages;
    }

    public void setMessages(Collection<Messages> messages) {
        this.messages = messages;
    }
}
