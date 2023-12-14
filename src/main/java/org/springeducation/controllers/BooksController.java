package org.springeducation.controllers;

import jakarta.validation.Valid;
import org.springeducation.dao.BookDAO;
import org.springeducation.dao.PersonDAO;
import org.springeducation.models.Book;
import org.springeducation.models.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;

    public BooksController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String showAll(Model model) {
        model.addAttribute("books", bookDAO.showAll());
        return "books/allBooks";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person")Person person) {
        model.addAttribute("book", bookDAO.show(id));
        model.addAttribute("owner", bookDAO.showOwner(id));
        model.addAttribute("people", personDAO.showAll());
        return "books/showBook";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book")Book book) {
        return "books/newBook";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/newBook";
        bookDAO.create(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", bookDAO.show(id));
        return "books/updateBook";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "books/updateBook";
        bookDAO.update(id, book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/add")
    public String addOwner(@ModelAttribute("person")Person person, @PathVariable("id") int id) {
        bookDAO.addOwner(id, person.getId());
        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/delete")
    public String deleteOwner(@PathVariable("id") int id) {
        bookDAO.deleteOwner(id);
        return "redirect:/books/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }
}
