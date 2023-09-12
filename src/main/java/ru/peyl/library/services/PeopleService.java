package ru.peyl.library.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.peyl.library.models.Book;
import ru.peyl.library.models.Person;
import ru.peyl.library.repositories.PeopleRepository;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> getAllPeople() {
        return peopleRepository.findAll();
    }

    public Person getPersonById(final int id) {
        return peopleRepository.findById(id).orElse(null);
    }

    @Transactional
    public void addPerson(final Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void updatePerson(final Person person, final int id) {
        person.setBooks(peopleRepository.findById(id).get().getBooks());
        peopleRepository.save(person);
    }

    @Transactional
    public void deletePerson(final int id) {
        peopleRepository.delete(getPersonById(id));
    }

    public Optional<Person> getPersonByName(final String name, final int id) {
        return peopleRepository.findByNameAndIdNot(name, id);
    }

    @Transactional
    public List<Book> getAllBooksByPersonId(final int id) {
        Optional<Person> optionalPerson = peopleRepository.findById(id);
        if (optionalPerson.isPresent()) {
            Hibernate.initialize(optionalPerson.get().getBooks());
            final List<Book> books = optionalPerson.get().getBooks();
            for (final Book book : books) {
                book.setOverdue(isOverdue(book.getTakenAT()));
            }
            return books;
        }
        return Collections.emptyList();
    }

    private boolean isOverdue(final Date takenAT) {
        if (takenAT == null) {
            return false;
        }
        long diffirenceBetweenDays = new Date().getTime() - takenAT.getTime();
        return diffirenceBetweenDays >= 864000000;
    }
}
