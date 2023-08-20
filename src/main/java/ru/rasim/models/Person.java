package ru.rasim.models;

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
public class Person {

    private Integer id;

    @NotEmpty(message = "Name must not be empty")
    @Size(min = 2, message = "Name must contain minimum 2 character")
    private String name;

    @NotEmpty(message = "Surname must not be empty")
    @Size(min = 2, message = "Surname must contain minimum 2 character")
    private String surname;

    @Min(value = 1, message = "Age must be greater or equals 1")
    private Integer age;

    @Email
    @NotEmpty(message = "Email must not be empty")
    private String email;

    public String getFullName() {
        return surname + " " + name;
    }
}
