package ru.peyl.library.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.peyl.library.models.Book;
import ru.peyl.library.models.Person;
import ru.peyl.library.repositories.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BookRepository bookRepository;

    @Autowired
    public BooksService(final BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(final int id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Transactional
    public void updateBook(final int id, final Book book) {
        book.setBookId(id);
        book.setOwner(bookRepository.findById(id).get().getOwner());
        bookRepository.save(book);
    }

    @Transactional
    public void addBook(final Book book) {
        bookRepository.save(book);
    }

    @Transactional
    public void deleteBook(final int id) {
        bookRepository.delete(getBookById(id));
    }

    @Transactional
    public void appointBook(final Person person, final int bookId) {
        final Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setOwner(person);
            //person.getBooks().add(book);
            bookRepository.save(book);
        }
    }

    @Transactional
    public void releaseBook(int bookId) {
        final Optional<Book> optionalBook = bookRepository.findById(bookId);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            final Person owner = book.getOwner();
            owner.getBooks().remove(book);
            book.setOwner(null);
            bookRepository.save(book);
        }
    }

    public Optional<Person> getBookOwner(final int bookId) {
        final Optional<Book> book = bookRepository.findById(bookId);
        return book.map(Book::getOwner);
    }
}
