package com.nuchat.capricorn.dto;


import com.nuchat.capricorn.model.Conversation;
import com.nuchat.capricorn.model.MessageType;
import com.nuchat.capricorn.model.User;


import java.util.Date;

public class MessageWebSocketDTO {

    private MessageType message_type;
    private String message;
    private String channelId;

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

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
}