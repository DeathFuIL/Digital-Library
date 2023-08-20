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

    private final PersonsRepositoryImpl personsRepository;

    private final BooksRepositoryImpl booksRepository;

    @Autowired
    public BookingsController(BookingsRepositoryImpl bookingsRepository, PersonsRepositoryImpl personsRepository, BooksRepositoryImpl booksRepository) {
        this.bookingsRepository = bookingsRepository;
        this.personsRepository = personsRepository;
        this.booksRepository = booksRepository;
    }

    @GetMapping()
    public String showAll(Model model) {
        model.addAttribute("bookings", bookingsRepository.showAll());

        return "booking/showAll";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("booking", bookingsRepository.show(id));

        return "booking/show";
    }

    @GetMapping("/active")
    public String showAllActive(Model model) {
        model.addAttribute("activeBookings", bookingsRepository.showAllActive());

        return "booking/active";
    }

    @GetMapping("/finished")
    public String showAllFinished(Model model) {
        model.addAttribute("finishedBookings", bookingsRepository.showAllFinished());

        return "booking/finished";
    }


    @GetMapping("/new")
    public String newBooking(@ModelAttribute("booking")Booking booking) {
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


    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookingsRepository.delete(id);
        return "redirect:/bookings";
    }

    @PatchMapping("/{id}")
    public String finish(@PathVariable("id") int id) {
        bookingsRepository.finish(id);
        return "redirect:/bookings/" + id;
    }
}
