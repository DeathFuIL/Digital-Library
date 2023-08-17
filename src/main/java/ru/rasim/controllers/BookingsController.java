package ru.rasim.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.rasim.models.Booking;
import ru.rasim.repositories.impl.BookingsRepositoryImpl;

import javax.validation.Valid;

@Controller
@RequestMapping("/bookings")
public class BookingsController {

    private final BookingsRepositoryImpl bookingsRepository;

    @Autowired
    public BookingsController(BookingsRepositoryImpl bookingsRepository) {
        this.bookingsRepository = bookingsRepository;
    }

    @GetMapping()
    public String showAll(Model model) {
        model.addAttribute("booking", bookingsRepository.showAll());

        return "booking/showAll";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("booking", bookingsRepository.show(id));

        return "booking/show";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("booking")Booking booking) {
        return "booking/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("booking") @Valid Booking booking,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "booking/new";

        bookingsRepository.save(booking);
        return "redirect:/bookings";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("booking", bookingsRepository.show(id));

        return "booking/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("booking") @Valid Booking booking, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "booking/edit";

        bookingsRepository.update(id, booking);
        return "redirect:/bookings";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookingsRepository.delete(id);
        return "redirect:/bookings";
    }

}
