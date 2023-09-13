package ru.rasim.models;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Person")
public class Person {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotEmpty(message = "Name must not be empty")
    @Size(min = 2, message = "Name must contain minimum 2 character")
    private String name;

    @Column(name = "surname")
    @NotEmpty(message = "Surname must not be empty")
    @Size(min = 2, message = "Surname must contain minimum 2 character")
    private String surname;

    @Column(name = "age")
    @Min(value = 1, message = "Age must be greater or equals 1")
    private Integer age;

    @Column(name = "email")
    @Email
    @NotEmpty(message = "Email must not be empty")
    private String email;

    public String getFullName() {
        return surname + " " + name;
    }
}
