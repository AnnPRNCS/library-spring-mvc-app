package ru.peyl.library.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.peyl.library.models.Book;
import ru.peyl.library.models.Person;
import ru.peyl.library.services.PeopleService;
import ru.peyl.library.utils.PersonValidator;

import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PeopleService peopleService;
    private final PersonValidator personValidator;

    @Autowired
    public PeopleController(final PeopleService peopleService,
                            final PersonValidator personValidator) {
        this.peopleService = peopleService;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String getAllPeople(final Model model) {
        final List<Person> people = peopleService.getAllPeople();
        model.addAttribute("people", people);
        return "people/all_people";
    }

    @GetMapping("/{id}")
    public String getPersonById(@PathVariable("id") final int id, final Model model) {
        final List<Book> allBooksByPersonId = peopleService.getAllBooksByPersonId(id);
        Person person = peopleService.getPersonById(id);
        model.addAttribute("booksByPerson", allBooksByPersonId);
        model.addAttribute("person", person);
        return "people/person";
    }

    @GetMapping("/new")
    public String getMappingAddNewPerson(@ModelAttribute("person") final Person person) {
        return "people/new_person";
    }

    @PostMapping()
    public String postMappingAddPerson(@ModelAttribute("person") @Valid final Person person,
                                       final BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/new_person";
        }
        peopleService.addPerson(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String getMappingEditPerson(final Model model, @PathVariable("id") final int id) {
        Person person = peopleService.getPersonById(id);
        model.addAttribute("person", person);
        return "people/update_person";
    }

    @PatchMapping("/{id}")
    public String patchMappingEditPerson(@PathVariable("id") final int id,
                                         @ModelAttribute("person") @Valid final Person person,
                                         final BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/update_person";
        }
        peopleService.updatePerson(person, id);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") final int id) {
        peopleService.deletePerson(id);
        return "redirect:/people";
    }
}
