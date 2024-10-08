package ru.academits.java.kononov.phonebookhibernate.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.academits.java.kononov.phonebookhibernate.converter.ContactDtoToContactConverter;
import ru.academits.java.kononov.phonebookhibernate.converter.ContactToContactDtoConverter;
import ru.academits.java.kononov.phonebookhibernate.dto.BaseResponse;
import ru.academits.java.kononov.phonebookhibernate.dto.ContactDto;
import ru.academits.java.kononov.phonebookhibernate.exception.ValidationException;
import ru.academits.java.kononov.phonebookhibernate.service.ContactsService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/contacts")
@Slf4j
public class ContactsController {
    private final ContactsService contactsService;
    private final ContactDtoToContactConverter contactDtoToContactConverter;
    private final ContactToContactDtoConverter contactToContactDtoConverter;

    public ContactsController(ContactsService contactsService, ContactDtoToContactConverter contactDtoToContactConverter, ContactToContactDtoConverter contactToContactDtoConverter) {
        this.contactsService = contactsService;
        this.contactDtoToContactConverter = contactDtoToContactConverter;
        this.contactToContactDtoConverter = contactToContactDtoConverter;
    }

    @GetMapping
    public List<ContactDto> getContacts(@RequestParam(required = false) String term) {
        try {
            return contactToContactDtoConverter.convert(contactsService.getContacts(term));
        } catch (ValidationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PostMapping
    public BaseResponse addContact(@Valid @RequestBody ContactDto contact) {
        try {
            contactsService.addContact(contactDtoToContactConverter.convert(contact));

            return BaseResponse.createSuccessResponse();
        } catch (ValidationException e) {
            return handleValidationException(e);
        }
    }

    @DeleteMapping
    public BaseResponse deleteContact(@RequestParam Long id) {
        try {
            contactsService.deleteContact(id);

            return BaseResponse.createSuccessResponse();
        } catch (ValidationException e) {
            return handleValidationException(e);
        }
    }

    private static BaseResponse handleValidationException(ValidationException e) {
        log.error("ValidationException: {}", e.getMessage(), e);

        return BaseResponse.createErrorResponse(e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public BaseResponse handleConstraintViolationException(ConstraintViolationException e) {

        return BaseResponse.createErrorResponse(e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("; ")));
    }
}
