package com.nuchat.capricorn.dto;

import com.nuchat.capricorn.model.User;

public interface ListNotificationDTO {
    Integer getId();
    String getContent();
    String getSender_from();
    boolean getIs_confirm();
}
