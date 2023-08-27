package ru.peyl.library.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


public class Person {
    @NotNull(message = "Name shouldn't be null")
    @NotEmpty(message = "Name shouldn't be empty")
    private String name;
    @Min(value = 1900, message = "You enter incorrest year of birth, it shout be more 1900")
    private int birthYear;
    private int id;

    public Person() {
    }

    public Person(String name, int birthYear, int id) {
        this.name = name;
        this.birthYear = birthYear;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }
}
