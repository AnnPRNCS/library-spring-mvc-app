package ru.peyl.library.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.peyl.library.daos.BooksDAO;
import ru.peyl.library.daos.PeopleDAO;
import ru.peyl.library.models.Book;
import ru.peyl.library.models.Person;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BooksDAO booksDAO;
    private final PeopleDAO peopleDAO;

    @Autowired
    public BooksController(final BooksDAO booksDAO, final PeopleDAO peopleDAO) {
        this.booksDAO = booksDAO;
        this.peopleDAO = peopleDAO;
    }

    @GetMapping()
    public String getAllBooks(final Model model) {
        List<Book> books = booksDAO.getAllBooks();
        model.addAttribute("books", books);
        return "books/all_books";
    }

    @GetMapping("/{id}")
    public String getBookById(@PathVariable("id") final int bookId, final Model model,
                              @ModelAttribute("person") final Person person) {
        final Book book = booksDAO.getBookById(bookId);
        List<Person> people = peopleDAO.getAllPeople();
        Optional<Person> bookOwner = booksDAO.getBookOwner(bookId);
        if (bookOwner.isPresent()) {
            model.addAttribute("owner", bookOwner.get());
        } else {
            model.addAttribute("people", people);
        }
        model.addAttribute("book", book);
        return "books/book";
    }

    @GetMapping("/new")
    public String getMappingAddNewBook(@ModelAttribute("book") final Book book) {
        return "books/new_book";
    }

    @PatchMapping("/{id}/appoint")
    public String appointBook(@PathVariable("id") final int bookId,
                              @ModelAttribute("person") final Person person) {
        booksDAO.appointBook(person, bookId);
        return "redirect:/books/" + bookId;
    }

    @PatchMapping("/{id}/release")
    public String releaseBook(@PathVariable("id") final int bookId) {
        booksDAO.releaseBook(bookId);
        return "redirect:/books/" + bookId;
    }

    @PostMapping()
    public String postMappingAddNewBook(@ModelAttribute("book") @Valid final Book book,
                                        final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/new_book";
        }
        booksDAO.addBook(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String getMappingUpdateBook(@PathVariable("id") final int id, final Model model) {
        final Book book = booksDAO.getBookById(id);
        model.addAttribute("book", book);
        return "books/update_book";
    }

    @PatchMapping("/{id}")
    public String postMappingUpdateBook(@PathVariable("id") final int id,
                                        @ModelAttribute("book") @Valid final Book book,
                                        final BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/update_book";
        }
        booksDAO.updateBook(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("{id}")
    public String deleteBook(@PathVariable("id") final int id) {
        booksDAO.deleteBook(id);
        return "redirect:/books";
    }
}
