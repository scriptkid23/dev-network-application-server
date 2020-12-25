package com.nuchat.capricorn.dto;

import com.nuchat.capricorn.model.Messages;
import com.nuchat.capricorn.model.Participants;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public class CreateConversationDTO {

    private List<String> receiver; // using email for get receiver detail;

    public List<String> getReceiver() {
        return receiver;
    }

    public void setReceiver(List<String> receiver) {
        this.receiver = receiver;
    }
}