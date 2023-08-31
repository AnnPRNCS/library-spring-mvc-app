package ru.peyl.library.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.peyl.library.daos.PeopleDAO;
import ru.peyl.library.models.Person;

import java.util.Optional;

@Component
public class PersonValidator implements Validator {
    private final PeopleDAO peopleDAO;

    @Autowired
    public PersonValidator(PeopleDAO peopleDAO) {
        this.peopleDAO = peopleDAO;
    }

    @Override
    public boolean supports(final Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        final Person person = (Person) target;
        Optional<Person> personByName = peopleDAO.getPersonByName(person.getName());
        if (personByName.isPresent()) {
            errors.rejectValue("name", "", "User with name like this is already exist in DB");
        }
    }
}
