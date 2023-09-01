package ru.peyl.library.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.peyl.library.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PeopleDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PeopleDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> getAllPeople() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person getPersonById(final int id) {
        return jdbcTemplate.queryForStream("SELECT * FROM Person WHERE person_id=?", new BeanPropertyRowMapper<>(Person.class),
                new Object[]{id}).findAny().orElse(null);
    }

    public void addPerson(final Person person) {
        jdbcTemplate.update("INSERT INTO Person(name, birth_year) VALUES (?,?)", person.getName(), person.getBirthYear());
    }

    public void updatePerson(final Person person, final int id) {
        jdbcTemplate.update("UPDATE Person SET name=?, birth_year=? WHERE person_id=?", person.getName(), person.getBirthYear(), id);
    }

    public void deletePerson(final int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE person_id=?", id);
    }

    public Optional<Person> getPersonByName(final String name) {
        return jdbcTemplate.queryForStream("SELECT * FROM Person WHERE name=?",
                new BeanPropertyRowMapper<>(Person.class), new Object[]{name}).findAny();
    }

    public Person getPersonByBook(final int bookId) {
        return jdbcTemplate.queryForStream("SELECT Person.person_id, Person.name, birth_year FROM Person LEFT JOIN Book ON Person.person_id = Book.person_id WHERE book_id=?",
                new BeanPropertyRowMapper<>(Person.class), new Object[]{bookId}).findAny().orElse(new Person());
    }
}
