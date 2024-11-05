package ua.test.library.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    private int id;
    @NotNull
    @NotEmpty(message = "Будь ласка, введіть ім'я")
    @Size(min = 2, max = 200, message = "Введіть валідне ім'я")
    @Pattern(regexp = "^[А-ЯІЇЄҐ][А-ЯІЇЄҐа-яіїєґ'’ -]*$",
            message = "Ім'я повинно містити тільки українські літери, починатися з великої літери і може містити дефіс")
    private String fullName;
    @NotNull(message = "Вік не може бути 0")
    @Min(value = 18, message = "Вік має бути більше 18 років")
    @Max(value = 110, message = "Вік має бути менше 110 років")
    private int age;

}
