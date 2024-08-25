package ru.academits.java.kononov.phonebookhibernate.converter;

import org.springframework.stereotype.Service;
import ru.academits.java.kononov.phonebookhibernate.entity.Contact;
import ru.academits.java.kononov.phonebookhibernate.dto.ContactDto;

@Service
public class ContactDtoToContactConverter implements Converter<ContactDto, Contact> {
    @Override
    public Contact convert(ContactDto source) {
        return new Contact(
                source.getId(),
                source.getFirstName(),
                source.getLastName(),
                source.getPhoneNumber()
        );
    }
}
