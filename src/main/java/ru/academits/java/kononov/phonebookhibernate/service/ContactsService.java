package ru.academits.java.kononov.phonebookhibernate.service;

import ru.academits.java.kononov.phonebookhibernate.entity.Contact;
import ru.academits.java.kononov.phonebookhibernate.exception.ValidationException;

import java.util.List;

public interface ContactsService {
    List<Contact> getContacts(String term) throws ValidationException;

    void addContact(Contact contact) throws ValidationException;

    void deleteContact(Long id) throws ValidationException;

    void deleteRandomContact() throws ValidationException;
}
