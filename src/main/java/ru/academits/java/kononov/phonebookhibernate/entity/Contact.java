package ru.academits.java.kononov.phonebookhibernate.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contact_seq")
    @SequenceGenerator(name = "contact_seq", sequenceName = "contact_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, length = 40)
    @NotBlank(message = "Contact firstName cannot be empty")
    private String firstName;

    @Column(nullable = false, length = 40)
    @NotBlank(message = "Contact lastName cannot be empty")
    private String lastName;

    @Column(unique = true, nullable = false, length = 20)
    @NotBlank(message = "Contact phoneNumber cannot be empty")
    private String phoneNumber;

    @Column(unique = true, length = 254)
    @Email(message = "Invalid email")
    private String email;
}
