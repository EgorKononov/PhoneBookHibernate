package ru.academits.java.kononov.phonebookhibernate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContactDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
}
