package ua.test.library.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.test.library.dao.BookDAO;
import ua.test.library.model.Book;
import ua.test.library.model.Person;

import java.time.LocalDate;

@Component
public class BooksValidator implements Validator {
    private BookDAO bookDAO;

    @Autowired
    public BooksValidator(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;
        if (bookDAO.readByName(book.getName()).isPresent()
                && bookDAO.readByAuthor(book.getAuthor()).isPresent()
        ) {
            errors.rejectValue("name", "",
                    "This book is already exists");
        }
        if (book.getYearOfProduction() >= LocalDate.now().getYear()
                ||
                book.getYearOfProduction() <= 1500
        ) {
            errors.rejectValue("name", "",
                    "введіть валідну дату");
        }
    }
}
