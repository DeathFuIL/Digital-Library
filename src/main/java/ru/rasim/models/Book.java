package ru.rasim.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Book")
public class Book {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotEmpty(message = "Name must not be empty")
    private String name;

    @Column(name = "year")
    @NotNull(message = "Please, enter the year of publishing")
    @Min(value = 1, message = "Age must be greater or equals 1")
    private Integer year;

    @Column(name = "author")
    @NotEmpty(message = "Author must not be empty")
    private String author;

    @Column(name = "count")
    @PositiveOrZero(message = "The count of books cannot be less than 0")
    private Integer count;

    public String getFullName() {
        return name + ", " + author + ", " + year;
    }

}
