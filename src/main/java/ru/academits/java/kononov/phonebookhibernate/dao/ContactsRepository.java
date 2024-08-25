package ru.academits.java.kononov.phonebookhibernate.dao;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.academits.java.kononov.phonebookhibernate.entity.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactsRepository extends JpaRepository<Contact, Long> {
    @Query("""
            select c from Contact c
            where upper(c.firstName) like concat('%', :firstName, '%')
            or upper(c.lastName) like concat('%', :lastName, '%')
            or upper(c.phoneNumber) like concat('%', :phoneNumber, '%')""")
    List<Contact> findByTerm(String firstName, String lastName, String phoneNumber);

    Optional<Contact> findByPhoneNumberIgnoreCase(String phoneNumber);

    @Modifying
    @Transactional
    @Query("delete from Contact c where c.id = :id")
    int deleteByIdWithResult(Long id);
}
