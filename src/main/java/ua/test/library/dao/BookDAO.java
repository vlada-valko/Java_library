package ua.test.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ua.test.library.model.Book;
import ua.test.library.model.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(Book book) {
        jdbcTemplate.update("INSERT INTO book (name, author, yearofproduction) VALUES (?,?,?)",
                book.getName(), book.getAuthor(), book.getYearOfProduction());
    }

    public List<Book> readAll() {
        return jdbcTemplate.query("SELECT * FROM book",
                new BeanPropertyRowMapper<>(Book.class));
    }
    public Book readById(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE id = ?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }
    public Optional<Book> readByName(String name) {
        return jdbcTemplate.query("SELECT * FROM book WHERE name = ?",
                new Object[]{name}, new BeanPropertyRowMapper<>(Book.class)).stream().findAny();
    }
    public Optional<Book> readByAuthor(String author) {
        return jdbcTemplate.query("SELECT * FROM book WHERE author = ?",
                new Object[]{author}, new BeanPropertyRowMapper<>(Book.class)).stream().findAny();
    }

    public void update(int id, Book book) {
        jdbcTemplate.update("UPDATE book SET name = ?, author = ?, yearofproduction = ? WHERE id = ?",
                book.getName(), book.getAuthor(), book.getYearOfProduction(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE id = ?", id);
    }

    public void assign (int bookId, int personId) {
            jdbcTemplate.update("UPDATE book SET personid = ? WHERE id = ?", personId, bookId);

    }
    public Person showTaker(int id) {
        return jdbcTemplate.query("SELECT person.id, person.fullname FROM person " +
                                "LEFT JOIN book ON person.id = book.personid WHERE book.id = ?",
                        new Object[]{id},
                        new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }
    public void release(int bookId) {
        jdbcTemplate.update("UPDATE book SET personid = null WHERE id = ?", bookId);
    }
}
