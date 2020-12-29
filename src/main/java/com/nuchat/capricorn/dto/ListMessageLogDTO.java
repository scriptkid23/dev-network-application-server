package com.nuchat.capricorn.dto;

import java.util.Date;

public interface ListMessageLogDTO {
    Integer getUser_id();
    String getAvatar();
    String getFirst_name_receiver();
    String getLast_name_receiver();
    String getChannel_id();
    Date getCreated_at();
    String getMessage();
    Integer getSender_id();
    String getFirst_name_sender();
    String getLast_name_sender();
    Integer getMessage_type();
    String getEmail();
}
