package com.nuchat.capricorn.dto;

public class EditProfileRequestDTO {
    private String first_name;
    private String last_name;
    private String avatar;
    private String phone_number;
    private String bio;

    public EditProfileRequestDTO(String first_name, String last_name, String avatar, String bio, String phone_number) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.avatar = avatar;
        this.bio = bio;
        this.phone_number = phone_number;

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}
