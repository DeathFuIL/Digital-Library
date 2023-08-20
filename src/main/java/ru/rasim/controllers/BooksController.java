package ru.rasim.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.rasim.models.Book;
import ru.rasim.repositories.impl.BooksRepositoryImpl;

import jakarta.validation.Valid;


@Controller
@RequestMapping("/books")
public class BooksController {

    private final BooksRepositoryImpl booksRepository;

    @Autowired
    public BooksController(BooksRepositoryImpl booksRepository) {
        this.booksRepository = booksRepository;
    }

    @GetMapping()
    public String showAll(Model model) {
        model.addAttribute("books", booksRepository.showAll());
        return "book/showAll";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("book", booksRepository.show(id));
        return "book/show";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "book/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "book/new";

        booksRepository.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("book", booksRepository.show(id));
        return "book/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "book/edit";

        booksRepository.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksRepository.delete(id);
        return "redirect:/books";
    }

}
