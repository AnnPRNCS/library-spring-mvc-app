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
    private int personId;

    public Person() {
    }

    public Person(String name, int birthYear, int personId) {
        this.name = name;
        this.birthYear = birthYear;
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }
}
