package com.nuchat.capricorn.dto;

import com.nuchat.capricorn.model.Attachments;
import com.nuchat.capricorn.model.Conversation;
import com.nuchat.capricorn.model.MessageType;
import com.nuchat.capricorn.model.User;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

public class MessageWebSocketResponse {

    private Integer id;
    private String channel_id;
    private String guid;
    private MessageType message_type;
    private String message;
    private Date created_at;
    private Collection<Attachments> attachments;
    private User user;
    private Conversation conversation;

    public String getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

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

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Collection<Attachments> getAttachments() {
        return attachments;
    }

    public void setAttachments(Collection<Attachments> attachments) {
        this.attachments = attachments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


}
