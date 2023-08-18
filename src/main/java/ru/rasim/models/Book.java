package ru.rasim.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    private Integer id;

    @NotEmpty(message = "Name must not be empty")
    private String name;

    @Min(value = 0, message = "Age must be greater than 0")
    private Integer year;

    @NotEmpty(message = "Author must not be empty")
    private String author;

    public String getFullName() {
        return name + ", " + author + ", " + year;
    }

}
