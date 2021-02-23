//This is called Spring Rest API's Controller

package com.ap.mycontact.controller;

import com.ap.mycontact.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ap.mycontact.model.Contact;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")

public class ContactController {

    @Autowired
    private ContactRepository contactRepository;

    //Now let's create the methods
    //Grab all contacts

    @GetMapping("/contacts")
    public ResponseEntity<List<Contact>> getAllContacts(@RequestParam(required = false) String name){
        try {
            List<Contact> contacts = new ArrayList<Contact>();

            if (name == null)
                contactRepository.findAll().forEach(contacts::add);
            else
                contactRepository.findByName(name).forEach(contacts::add);

            if (contacts.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(contacts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/contacts/{id}")
    public ResponseEntity<Contact> getContactsById(@PathVariable("id") Long id){
        Optional<Contact> contactData = contactRepository.findById(id);

        if (contactData.isPresent()) {
            return new ResponseEntity<>(contactData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //POST
    @PostMapping("/contacts")
    public ResponseEntity<Contact> creatContact(@RequestBody Contact contact){
        try {
            Contact _contact = contactRepository
                    .save(new Contact(contact.getName(), contact.getPhone(), contact.getEmail()));
            return new ResponseEntity<>(_contact, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/contacts/{id}")
    public ResponseEntity<Contact> updateContact(@PathVariable("id") Long id, @RequestBody Contact contact ){
        Optional<Contact> contactData = contactRepository.findById(id);

        if (contactData.isPresent()) {
            Contact _cont = contactData.get();
            _cont.setName(contact.getName());
            _cont.setPhone(contact.getPhone());
            _cont.setEmail(contact.getEmail());
            return new ResponseEntity<>(contactRepository.save(_cont), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Delete the single contact by id
    @DeleteMapping("/contacts/{id}")
    public ResponseEntity<HttpStatus> deleteContact(@PathVariable("id") long id) {
        try {
            contactRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //It not good idea delete all, but I just create it if needed
    @DeleteMapping("/contacts")
    public ResponseEntity<HttpStatus> deleteAllContacts() {
        try {
            contactRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/contacts/email")
    public ResponseEntity<List<Contact>> findByEmail(String email) {
        try {
            List<Contact> contacts = contactRepository.findByEmail(email);

            if (contacts.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(contacts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
