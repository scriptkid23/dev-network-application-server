package com.nuchat.capricorn.model;

import javax.persistence.*;

@Entity
@AssociationOverrides({
        @AssociationOverride(name = "primaryKey.user",joinColumns = @JoinColumn(name = "user_id")),
        @AssociationOverride(name = "primaryKey.contacts",joinColumns = @JoinColumn(name = "contact_id")),
})
public class UserContact{
    private UserContactId primaryKey = new UserContactId();
    private String first_name;
    private String last_name;

    @EmbeddedId
    public UserContactId getPrimaryKey(){
        return primaryKey;
    }
    public void setPrimaryKey(UserContactId primaryKey){
        this.primaryKey = primaryKey;
    }

    @Transient
    public User getUser(){
        return getPrimaryKey().getUser();
    }
    public void setUser(User users){
        getPrimaryKey().setUser(users);
    }
    @Transient
    public Contacts getContact(){
        return getPrimaryKey().getContacts();
    }
    public void setContact(Contacts contacts){
        getPrimaryKey().setContacts(contacts);
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
}
