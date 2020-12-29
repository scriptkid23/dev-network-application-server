package com.nuchat.capricorn.dto;

import java.util.Date;

public class ContactsDTO {
    private Integer id;
    private Date created_at;
    private String email;
    private String first_name;
    private String last_name;
    private String phone;

    public ContactsDTO(Integer id, Date created_at, String email, String first_name, String last_name, String phone) {
        this.id = id;
        this.created_at = created_at;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}