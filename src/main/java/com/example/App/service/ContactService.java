package com.example.App.service;

import com.example.App.dto.ContactDto;
import com.example.App.entity.Contact;

import java.util.List;

public interface ContactService {
    void saveContact(ContactDto contactDto);
    Contact findByEmail(String email);
    Contact findByPhone(String phone);// add
    List<ContactDto> findAllContacts();
}
