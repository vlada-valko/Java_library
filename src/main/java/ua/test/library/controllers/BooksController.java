package ua.test.library.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.test.library.dao.BookDAO;
import ua.test.library.dao.PeopleDAO;
import ua.test.library.model.Book;
import ua.test.library.model.Person;
import ua.test.library.util.BooksValidator;

@Controller
@RequestMapping("/books")
@AllArgsConstructor
public class BooksController {
    BookDAO bookDAO;
    PeopleDAO peopleDAO;
    BooksValidator booksValidator;

    @GetMapping("/new")
    public String createForm(@ModelAttribute("book") Book book){
        return "books/createForm";
    }
    @PostMapping()
    public String create (@ModelAttribute ("book") @Valid Book book,
                          BindingResult bindingResult) {
        booksValidator.validate(book,bindingResult);
        if (bindingResult.hasErrors()) {
            return "books/createForm";
        }
        bookDAO.create(book);
        return "redirect:/books";
    }

    @GetMapping()
    public String readAll(Model model){
        model.addAttribute("books", bookDAO.readAll());
        return "books/readAll";
    }
    @GetMapping("{id}")
    public String readById(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDAO.readById(id));
        model.addAttribute("people", peopleDAO.readAll());
        model.addAttribute("person", new Person());
        model.addAttribute("taker", bookDAO.showTaker(id));
        return "books/readById";
    }

  @GetMapping("{id}/edit")
    public String updateForm (@PathVariable("id") int id,
                         Model model ) {
        model.addAttribute("book", bookDAO.readById(id));
        return "books/updateForm";
    }
    @PatchMapping("{id}")
    public String update (@PathVariable("id") int id, @Valid Book book,
                          BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "books/updateForm";
        }
        bookDAO.update(id,book);
        return "redirect:/books";
    }

    @DeleteMapping ("{id}/delete")
    public String delete (@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }

    @PostMapping ("{id}/assign")
    public String assign (@PathVariable("id") int bookId,  @RequestParam("id") int personId) {
        bookDAO.assign(bookId, personId);
        return "redirect:/books/{id}";
    }
    @PostMapping("{id}/release")
    public String release (@PathVariable("id") int bookId) {
        bookDAO.release(bookId);
        return "redirect:/books/{id}";
    }
}
