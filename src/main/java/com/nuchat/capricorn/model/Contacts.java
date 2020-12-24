package com.nuchat.capricorn.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
public class Contacts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Date create_at;
    private String email;
    private String first_name;
    private String last_name;
    private String phone;

    @OneToMany(mappedBy = "primaryKey.contacts",cascade = CascadeType.ALL)
    private Collection<UserContact> user_contacts;



    public Date getCreate_at() {
        return create_at;
    }

    public void setCreate_at(Date create_at) {
        this.create_at = create_at;
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
