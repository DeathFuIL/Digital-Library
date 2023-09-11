package ru.rasim.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.rasim.models.Booking;
import ru.rasim.models.Person;
import ru.rasim.repositories.impl.BookingsRepositoryImpl;
import ru.rasim.repositories.impl.BooksRepositoryImpl;
import ru.rasim.repositories.impl.PersonsRepositoryImpl;

import jakarta.validation.Valid;
import java.util.List;



@Controller
@RequestMapping("/persons")
public class PersonsController {

    private final PersonsRepositoryImpl personsRepository;

    private final BookingsRepositoryImpl bookingsRepository;

    private final BooksRepositoryImpl booksRepository;


    @Autowired
    public PersonsController(PersonsRepositoryImpl personsRepository, BookingsRepositoryImpl bookingsRepository, BooksRepositoryImpl booksRepository) {
        this.personsRepository = personsRepository;
        this.bookingsRepository = bookingsRepository;
        this.booksRepository = booksRepository;
    }

    @GetMapping()
    public String showAll(Model model) {
        model.addAttribute("persons", personsRepository.showAll());
        return "person/showAll";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        Person person = personsRepository.show(id);
        if (person == null) {
            return "person/error";
        }
        List<Booking> personBookings = bookingsRepository.showByPersonId(id);
        model.addAttribute("booksRepository", booksRepository);
        model.addAttribute("personBookings", personBookings);
        model.addAttribute("person", person);
        return "person/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "person/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "person/new";

        Long generatedId = personsRepository.save(person);
        return "redirect:/persons/" + generatedId;
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Long id, Model model) {
        model.addAttribute("person", personsRepository.show(id));
        return "person/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") Long id) {
        if (bindingResult.hasErrors())
            return "person/edit";

        personsRepository.update(id, person);
        return "redirect:/persons";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        personsRepository.delete(id);
        return "redirect:/persons";
    }
}
