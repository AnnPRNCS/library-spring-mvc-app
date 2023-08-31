package ru.peyl.library.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.peyl.library.daos.PeopleDAO;
import ru.peyl.library.models.Person;
import ru.peyl.library.utils.PersonValidator;

import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PeopleDAO peopleDAO;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(final PeopleDAO peopleDAO, PersonValidator personValidator) {
        this.peopleDAO = peopleDAO;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String getAllPeople(final Model model) {
        List<Person> people = peopleDAO.getAllPeople();
        model.addAttribute("people", people);
        return "people/all_people";
    }

    @GetMapping("/{id}")
    public String getPersonById(@PathVariable("id") final int id, final Model model) {
        //todo: провалидировать на null
        Person person = peopleDAO.getPersonById(id);
        model.addAttribute("person", person);
        return "people/person";
    }

    @GetMapping("/new")
    public String addNewPerson(@ModelAttribute("person") final Person person) {
        return "people/new_person";
    }

    @PostMapping()
    public String addPerson(@ModelAttribute("person") @Valid final Person person, final BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/new_person";
        }
        peopleDAO.addPerson(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(final Model model, @PathVariable("id") final int id) {
        model.addAttribute("person", peopleDAO.getPersonById(id));
        return "people/update_person";
    }

    @PatchMapping("/{id}")
    public String updatePerson(@PathVariable("id") final int id, @ModelAttribute("person") @Valid final Person person,
                               final BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/update_person";
        }
        peopleDAO.updatePerson(person, id);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") final int id) {
        peopleDAO.deletePerson(id);
        return "redirect:/people";
    }
}
