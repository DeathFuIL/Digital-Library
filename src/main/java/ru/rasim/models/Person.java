package ru.rasim.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Person {

    @Min(value = 0, message = "Please, select person")
    private Integer id;

    @NotEmpty(message = "Name must not be empty")
    @Size(min = 2, message = "Name must contain minimum 2 character")
    private String name;

    @NotEmpty(message = "Surname must not be empty")
    @Size(min = 2, message = "Surname must contain minimum 2 character")
    private String surname;

    @Min(value = 0, message = "Age must be greater than 0")
    private Integer age;

    @Email
    @NotEmpty(message = "Email must not be empty")
    private String email;

    public String getFullName() {
        return surname + " " + name;
    }
}
