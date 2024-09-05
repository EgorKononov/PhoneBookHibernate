package ru.academits.java.kononov.phonebookhibernate.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.academits.java.kononov.phonebookhibernate.dao.ContactsRepository;
import ru.academits.java.kononov.phonebookhibernate.entity.Contact;
import ru.academits.java.kononov.phonebookhibernate.exception.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
class ContactsServiceImplTest {
    private final Contact contact1 = new Contact(1L, "Ivan", "Ivanov", "8123456789", "ivanov@gmail.com");
    private final Contact contact2 = new Contact(2L, "Petr", "Petrov", "8123456788", "petrov@gmail.com");

    @TestConfiguration
    static class ContactsServiceImplTestContextConfiguration {
        @Bean
        public ContactsService contactsService(ContactsRepository contactsRepository) {
            return new ContactsServiceImpl(contactsRepository);
        }
    }

    @BeforeEach
    public void setUp() {
        List<Contact> contacts = new ArrayList<>();
        contacts.add(contact1);
        contacts.add(contact2);
        Mockito.when(contactsRepository.findAll()).thenReturn(contacts);
    }

    @Autowired
    private ContactsService contactsService;

    @MockBean
    private ContactsRepository contactsRepository;

    @Test
    void testGetContacts() throws ValidationException {
        List<Contact> contacts = contactsService.getContacts(null);
        assertEquals(contacts.size(), 2);
        assertTrue(contacts.stream().anyMatch(contact -> Objects.equals(contact, contact1)));
        assertTrue(contacts.stream().anyMatch(contact -> Objects.equals(contact, contact2)));
    }
}