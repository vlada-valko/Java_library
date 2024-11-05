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
public class PeopleDAO {
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    public PeopleDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(Person person) {
        jdbcTemplate.update("INSERT INTO person (fullname, age) VALUES (?,?)",
                person.getFullName(), person.getAge());
    }

    public List<Person> readAll() {
        return jdbcTemplate.query("SELECT * FROM person",
                new BeanPropertyRowMapper<>(Person.class));
    }
    public Person readById(int id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE id = ?",
                        new Object[]{id},
                        new BeanPropertyRowMapper<>(Person.class)).stream()
                .findAny().orElse(null);
    }
    public Optional<Person> readByFullName(String fullName) {
        return jdbcTemplate.query("SELECT * FROM person WHERE fullname = ?",
                        new Object[]{fullName},
                        new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny();
    }

    public void update(int id, Person person) {
        jdbcTemplate.update("UPDATE person SET fullname=?, age=? WHERE id = ?",
                person.getFullName(), person.getAge(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE id = ?", id);
    }

    public List<Book> showBooks(int personId) {
        return jdbcTemplate.query("SELECT name, author FROM book WHERE personid = ?",
                new Object[]{personId}, new BeanPropertyRowMapper<>(Book.class));
    }
}

