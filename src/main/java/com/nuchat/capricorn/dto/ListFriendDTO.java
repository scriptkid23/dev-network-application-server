package com.nuchat.capricorn.dto;

import java.sql.Timestamp;
import java.util.Date;

public interface ListFriendDTO {
    Integer getId();
    String getFirst_name();
    String getLast_name();
    String getAvatar();
    String getBio();
    Timestamp getBirthday();
    String getEmail();
    boolean getGender();
    String getPhone_number();
}
