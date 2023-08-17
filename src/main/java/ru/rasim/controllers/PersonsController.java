package ru.rasim.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.rasim.models.Person;
import ru.rasim.repositories.impl.BookingsRepositoryImpl;
import ru.rasim.repositories.impl.PersonsRepositoryImpl;

import javax.validation.Valid;


@Controller
@RequestMapping("/persons")
public class PersonsController {

    private final PersonsRepositoryImpl personsRepository;

    private final BookingsRepositoryImpl bookingsRepository;


    @Autowired
    public PersonsController(PersonsRepositoryImpl personsRepository, BookingsRepositoryImpl bookingsRepository) {
        this.personsRepository = personsRepository;
        this.bookingsRepository = bookingsRepository;
    }

    @GetMapping()
    public String showAll(Model model) {
        model.addAttribute("persons", personsRepository.showAll());
        return "person/showAll";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("person", personsRepository.show(id));
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

        personsRepository.save(person);
        return "redirect:/persons";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("person", personsRepository.show(id));
        return "person/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "person/edit";

        personsRepository.update(id, person);
        return "redirect:/persons";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personsRepository.delete(id);
        return "redirect:/persons";
    }

    @GetMapping("/{id}/bookings")
    public String getPersonBookings(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("booking", bookingsRepository.showByPersonId(id));

        return "person/bookings";
    }
}