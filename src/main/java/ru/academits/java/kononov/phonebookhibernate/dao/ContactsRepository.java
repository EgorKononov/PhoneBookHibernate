package ru.academits.java.kononov.phonebookhibernate.dao;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.academits.java.kononov.phonebookhibernate.entity.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactsRepository extends JpaRepository<Contact, Long> {
    List<Contact> findByFirstNameContainsIgnoreCaseOrLastNameContainsIgnoreCaseOrPhoneNumberContainsIgnoreCaseOrEmailContainsIgnoreCase(
            String firstName,
            String lastName,
            String phoneNumber,
            String email);

    Optional<Contact> findByPhoneNumberIgnoreCase(String phoneNumber);

    Optional<Contact> findByEmailIgnoreCase(String email);

    @Modifying
    @Transactional
    @Query("delete from Contact c where c.id = :id")
    int deleteByIdWithResult(Long id);
}
