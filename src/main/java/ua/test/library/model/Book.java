package ua.test.library.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private int id;
    @NotEmpty(message = "Будь ласка, введіть назву книги")
    @Size(min = 1, max = 200, message = "Введіть валідну назву")
    @Pattern(regexp = "^[А-ЯІЇЄҐA-Z][А-ЯІЇЄҐа-яіїєґA-Za-z0-9'’_ -]*$",
            message = "Введіть валідну назву")
    private String name;
    @NotEmpty(message = "Будь ласка, введіть автора книги")
    @Size(min = 1, max = 200, message = "Введіть валідного автора")
    @Pattern(regexp = "^[А-ЯІЇЄҐA-Z][А-ЯІЇЄҐа-яіїєґA-Za-z0-9'’_ -]*$",
            message = "Введіть валідного автора")
    private String author;
    @NotNull
    @AssertTrue(message = "Year of production cannot be in the future")
    private boolean isYearOfProductionValid() {
        return yearOfProduction <= LocalDate.now().getYear() && yearOfProduction >= 1500;
    }
    private int yearOfProduction;
}
