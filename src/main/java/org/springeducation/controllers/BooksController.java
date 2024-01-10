package org.springeducation.controllers;

import jakarta.validation.Valid;
import org.springeducation.models.Book;
import org.springeducation.models.Person;
import org.springeducation.services.BooksService;
import org.springeducation.services.PeopleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BooksService booksService;
    private final PeopleService peopleService;

    public BooksController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }


    @GetMapping()
    public String showAll(Model model, @RequestParam(value = "page", required = false) Optional<Integer> page,
                          @RequestParam(value = "books_per_page", required = false) Optional<Integer> booksPerPage,
                          @RequestParam(value = "sort_by_year", required = false) boolean isSorted) {
        List<Book> books = new ArrayList<>();
        if (page.isPresent() && booksPerPage.isPresent()) {
            books = booksService.findSomeInPage(page.get(), booksPerPage.get(), isSorted);
        } else {
            books = booksService.findAll(isSorted);
        }
        model.addAttribute("books", books);
        return "books/allBooks";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model, @ModelAttribute("person")Person person) {
        model.addAttribute("book", booksService.findOne(id));
        model.addAttribute("owner", booksService.showOwner(id));
        model.addAttribute("people", peopleService.findAll());
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
        booksService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("book", booksService.findOne(id));
        return "books/updateBook";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "books/updateBook";
        booksService.update(id, book);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/add")
    public String addOwner(@ModelAttribute("person")Person person, @PathVariable("id") int id) {
        booksService.addOwner(id, person.getId());
        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/delete")
    public String deleteOwner(@PathVariable("id") int id) {
        booksService.deleteOwner(id);
        return "redirect:/books/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);
        return "redirect:/books";
    }

    @GetMapping("/search")
    String searchPage() {
        return "books/searchBook";
    }

    @PostMapping("/search")
    public String searchingBook(@RequestParam(name = "bookName", required = false) String bookName,
                                Model model) {
        model.addAttribute("searchedBooks", booksService.findByStartOfName(bookName));
        return "books/searchBook";
    }
}
