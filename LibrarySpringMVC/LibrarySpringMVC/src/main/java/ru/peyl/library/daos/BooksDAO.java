package ru.peyl.library.daos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.peyl.library.models.Book;
import ru.peyl.library.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class BooksDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BooksDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getAllBooks() {
        return jdbcTemplate.query("SELECT book_id, name, author, year FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book getBookById(final int id) {
        return jdbcTemplate.queryForStream("SELECT book_id, name, author, year FROM Book WHERE book_id=?", new BeanPropertyRowMapper<>(Book.class),
                new Object[]{id}).findAny().orElse(null);
    }

    public void updateBook(final int id, final Book book) {
        jdbcTemplate.update("UPDATE Book SET name=?, author=?, year=? WHERE book_id=?",
                book.getName(), book.getAuthor(), book.getYear(), id);
    }

    public void addBook(final Book book) {
        jdbcTemplate.update("INSERT INTO Book(name, author, year) VALUES (?, ?, ?)",
                book.getName(), book.getAuthor(), book.getYear());
    }

    public void deleteBook(final int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE book_id=?", id);
    }

    public void appointBook(final Person person, final int bookId) {
        jdbcTemplate.update("UPDATE Book SET person_id=? WHERE book_id=?", person.getPersonId(), bookId);
    }

    public void releaseBook(int bookId) {
        jdbcTemplate.update("UPDATE Book SET person_id=? WHERE book_id=?", null, bookId);
    }

    public Optional<Person> getBookOwner(final int bookId) {
        return jdbcTemplate.queryForStream("SELECT Person.*" +
                        "FROM Book JOIN Person ON Person.person_id = Book.person_id WHERE book_id=?",
                new BeanPropertyRowMapper<>(Person.class), new Object[]{bookId}).findAny();
    }
}
