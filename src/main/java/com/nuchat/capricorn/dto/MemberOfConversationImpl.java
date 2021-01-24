package com.nuchat.capricorn.dto;

public class MemberOfConversationImpl {
    private Integer id;
    private String avatar;
    private String email;
    private String first_name;
    private String last_name;
    private boolean is_present;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public boolean isIs_present() {
        return is_present;
    }

    public void setIs_present(boolean is_present) {
        this.is_present = is_present;
    }
}
