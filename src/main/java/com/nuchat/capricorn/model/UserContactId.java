package com.nuchat.capricorn.model;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;


@Embeddable
public class UserContactId implements Serializable{
    private Users users;
    private Contacts contacts;

    @ManyToOne(cascade = CascadeType.ALL)
    public Users getUser(){
        return users;
    }
    public void setUser(Users users){
        this.users = users;
    }
    @ManyToOne(cascade = CascadeType.ALL)
    public Contacts getContacts(){
        return  contacts;
    }
    public void setContacts(Contacts contacts){
        this.contacts = contacts;
    }
}
