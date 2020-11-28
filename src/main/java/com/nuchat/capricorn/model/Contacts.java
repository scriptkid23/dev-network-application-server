package com.nuchat.capricorn.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Entity
public class Contacts{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private String first_name;
    private String last_name;
    private String phone;
    private String email;
    private Date created_at;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "primaryKey.contacts",cascade = CascadeType.ALL)
    private Collection<UserContact> user_contacts;

    public void setId(Integer id) {
        Id = id;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public void setUser_contacts(Collection<UserContact> user_contacts) {
        this.user_contacts = user_contacts;
    }
}
