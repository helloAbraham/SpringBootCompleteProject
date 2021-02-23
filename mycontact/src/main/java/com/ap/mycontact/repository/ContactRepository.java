package com.ap.mycontact.repository;

import com.ap.mycontact.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface ContactRepository extends JpaRepository<Contact, Long> {

    List<Contact> findByEmail(String email);
    List<Contact> findByName(String name);

}
