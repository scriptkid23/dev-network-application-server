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

}
