package com.nuchat.capricorn.model;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;


@Embeddable
public class UserContactId implements Serializable{
    private User user;
    private Contacts contacts;

    @ManyToOne(cascade = CascadeType.ALL)
    public User getUser(){
        return user;
    }
    public void setUser(User users){
        this.user = users;
    }
    @ManyToOne(cascade = CascadeType.ALL)
    public Contacts getContacts(){
        return  contacts;
    }
    public void setContacts(Contacts contacts){
        this.contacts = contacts;
    }
}
