package ru.peyl.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.peyl.library.daos.BooksDAO;
import ru.peyl.library.models.Book;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BooksDAO booksDAO;

    @Autowired
    public BooksController(BooksDAO booksDAO) {
        this.booksDAO = booksDAO;
    }

    @GetMapping()
    public String getAllBooks(final Model model) {
        List<Book> books = booksDAO.getAllBooks();
        model.addAttribute("books", books);
        return "books/all_books";
    }

    @GetMapping("/{id}")
    public String getBookById(@PathVariable("id") final int id, final Model model) {
        final Book book = booksDAO.getBookById(id);
        model.addAttribute("book", book);
        return "books/book";
    }

    @GetMapping("/new")
    public String getMappingAddNewBook(@ModelAttribute("book") final Book book) {
        return "books/new_book";
    }

    @PostMapping()
    public String postMappingAddNewBook(@ModelAttribute("book") final Book book) {
        //todo:validation
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
    public String postMappingUpdateCurBook(@PathVariable("id") final int id, @ModelAttribute("book") final Book book) {
        //todo: validation
        booksDAO.updateBook(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("{id}")
    public String deleteBook(@PathVariable("id") final int id) {
        booksDAO.deleteBook(id);
        return "redirect:/books";
    }
}
