package com.ap.mycontact.model;


import org.springframework.context.annotation.ComponentScan;

import javax.persistence.*;


@Entity
@Table(name="contact_list")

public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column (name = "Name")
    private String name;

    @Column(name = "Phone")
    private String phone;

    @Column(name ="Email")
    private String email;

    public Contact() {
    }

    public Contact(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
