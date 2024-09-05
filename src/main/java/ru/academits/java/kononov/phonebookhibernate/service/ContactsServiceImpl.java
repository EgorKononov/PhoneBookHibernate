package ru.academits.java.kononov.phonebookhibernate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.query.EscapeCharacter;
import org.springframework.stereotype.Service;
import ru.academits.java.kononov.phonebookhibernate.dao.ContactsRepository;
import ru.academits.java.kononov.phonebookhibernate.entity.Contact;
import ru.academits.java.kononov.phonebookhibernate.exception.ValidationException;

import java.util.List;
import java.util.Random;

@Service
@Slf4j
public class ContactsServiceImpl implements ContactsService {
    private final ContactsRepository contactsRepository;

    public ContactsServiceImpl(ContactsRepository contactsRepository) {
        this.contactsRepository = contactsRepository;
    }

    @Override
    public List<Contact> getContacts(String term) {
        if (term == null || term.isBlank()) {
            return contactsRepository.findAll();
        }

        String termTrim = EscapeCharacter.DEFAULT.escape(term.trim());
        log.debug("Searching contacts for term [{}]", termTrim);

        return contactsRepository.findByFirstNameContainsIgnoreCaseOrLastNameContainsIgnoreCaseOrPhoneNumberContainsIgnoreCaseOrEmailContainsIgnoreCase(
                termTrim,
                termTrim,
                termTrim,
                termTrim);
    }

    @Override
    public void addContact(Contact contact) throws ValidationException {
        validateContact(contact);
        contact.setId(null);
        Contact savedContact = contactsRepository.save(contact);
        log.info("Contact added {}", savedContact);
    }

    private void validateContact(Contact contact) throws ValidationException {
        if (contact == null) {
            throw new ValidationException("Contact cannot be null");
        }

        validatePhoneNumberForUnique(contact.getPhoneNumber());
        validateEmailForUnique(contact.getEmail());
    }

    private void validatePhoneNumberForUnique(String phoneNumber) throws ValidationException {
        String phoneNumberUpperCaseTrim = phoneNumber.toUpperCase().trim();

        if (contactsRepository.findByPhoneNumberIgnoreCase(phoneNumberUpperCaseTrim).isPresent()) {
            throw new ValidationException("Contact with phone number [" + phoneNumberUpperCaseTrim + "] already exists");
        }
    }

    private void validateEmailForUnique(String email) throws ValidationException {
        String emailUpperCaseTrim = email.trim().toUpperCase();

        if (contactsRepository.findByEmailIgnoreCase(emailUpperCaseTrim).isPresent()) {
            throw new ValidationException("Contact with email [" + emailUpperCaseTrim + "] already exists");
        }
    }

    @Override
    public void deleteContact(Long id) throws ValidationException {
        if (contactsRepository.deleteByIdWithResult(id) == 1) {
            log.info("Contact with id=[{}] deleted successfully", id);
        } else {
            throw new ValidationException("Contact with id=[" + id + "] not found");
        }
    }

    @Override
    public void deleteRandomContact() {
        List<Contact> contacts = contactsRepository.findAll();

        if (!contacts.isEmpty()) {
            Contact randomContact = contacts.get(new Random().nextInt(contacts.size()));
            contactsRepository.deleteById(randomContact.getId());
            log.info("Random contact has been deleted: {}", randomContact);
        } else {
            log.info("Unable to delete random contact: no contacts found");
        }
    }
}
