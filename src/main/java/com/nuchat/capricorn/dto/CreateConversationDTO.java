package com.nuchat.capricorn.dto;

import com.nuchat.capricorn.model.Messages;
import com.nuchat.capricorn.model.Participants;

import java.util.Collection;
import java.util.Date;

public class CreateConversationDTO {

    private String receiver; // using email for get receiver detail;

    public String getReceiver() {
        return receiver;
    }
}