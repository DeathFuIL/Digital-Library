package ru.rasim.models;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    private Integer id;

    @NotEmpty(message = "Name must not be empty")
    private String name;

    @NotNull(message = "Please, enter the year of publishing")
    @Min(value = 1, message = "Age must be greater or equals 1")
    private Integer year;

    @NotEmpty(message = "Author must not be empty")
    private String author;

    public String getFullName() {
        return name + ", " + author + ", " + year;
    }

}
