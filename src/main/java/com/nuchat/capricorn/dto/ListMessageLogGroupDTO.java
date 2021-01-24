package com.nuchat.capricorn.dto;

import com.nuchat.capricorn.model.User;

import java.util.Date;
import java.util.List;

public interface ListMessageLogGroupDTO {
    Integer getId();
    Date getCreated_at();
    String getMessage();
    Integer getMessage_type();
    Integer getConversation_id();
    Integer getUser_id();
    String getChannel_id();
    String getTitle();
    String getAvatar();
    String getBio();
    String getFirst_name();
    String getLast_name();
    void setMember(List<MemberOfConversationDTO> member);
//    String getMember();
}
