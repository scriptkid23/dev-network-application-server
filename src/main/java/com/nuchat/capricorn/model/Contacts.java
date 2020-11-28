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
}
