package ua.test.library.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.test.library.dao.PeopleDAO;
import ua.test.library.model.Person;
import ua.test.library.util.PersonValidator;

@Controller
@RequestMapping("/people")
@AllArgsConstructor
public class PeopleController {
    private PeopleDAO peopleDAO;
    private PersonValidator personValidator;



    @GetMapping()
    public String showAll(Model model) {
        model.addAttribute("people", peopleDAO.readAll());
        return "people/readAll";
    }

    @GetMapping("/{id}")
    public String showById(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleDAO.readById(id));
        model.addAttribute("books", peopleDAO.showBooks(id));
        return "people/readById";
    }

    @GetMapping("/new")
    public String createForm(@ModelAttribute("person") Person person) {
        return "people/createForm";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/createForm";
        }
        peopleDAO.create(person);
        return "redirect:/people";
    }


    @GetMapping("/{id}/edit")
    public String updateForm(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleDAO.readById(id));
        return "people/updateForm";
    }

    @PatchMapping("{id}")
    public String update(@PathVariable("id") int id,
                         @ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "people/updateForm";
        }
        peopleDAO.update(id, person);
        return "redirect:/people";
    }


    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        peopleDAO.delete(id);
        return "redirect:/people";
    }

}
