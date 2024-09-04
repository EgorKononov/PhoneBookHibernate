package ru.academits.java.kononov.phonebookhibernate.converter;

import org.springframework.stereotype.Service;
import ru.academits.java.kononov.phonebookhibernate.entity.Contact;
import ru.academits.java.kononov.phonebookhibernate.dto.ContactDto;

@Service
public class ContactToContactDtoConverter implements Converter<Contact, ContactDto> {
    @Override
    public ContactDto convert(Contact source) {
        return new ContactDto(
                source.getId(),
                source.getFirstName(),
                source.getLastName(),
                source.getPhoneNumber(),
                source.getEmail()
        );
    }
}
