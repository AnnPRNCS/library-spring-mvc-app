package ru.peyl.library.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class Book {
    private int bookId;
    @NotEmpty(message = "Name of book shoudn't be empty")
    @NotNull(message = "Name of book shoudn't be null")
    private String name;
    @NotEmpty(message = "Author's name shoudn't be empty")
    @NotNull(message = "Author's name shoudn't be null")
    private String author;
    private int year;

    public Book() {
    }

    public Book(int bookId, String name, String author, int year) {
        this.bookId = bookId;
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
