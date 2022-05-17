package com.example.demo.controllers;

import com.example.demo.repo.User;
import com.example.demo.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Controller
public class UserController {

    /* set a default value from the application.properties  file */
    @Value( "${demo.coursename}" )
    private String someProperty;

    /* inject via its type the User repo bean - a singleton */
    @Autowired
    private UserRepository repository;

    private UserRepository getRepo() {
        return repository;
    }

    @GetMapping("/")
    public String main(User user, Model model) {
        model.addAttribute("course", someProperty);

        // the name "users"  is bound to the VIEW
        model.addAttribute("users", getRepo().findAll());
        return "index";
    }

    @GetMapping("/signup")
    public String showSignUpForm(User user, Model model) {
        //model.addAttribute("user", new User("noname","noemail"));
        return "add-user";
    }

    @PostMapping("/adduser")
    public String addUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-user";
        }

        getRepo().save(user);
        model.addAttribute("users", getRepo().findAll());
        return "index";
    }

    /*
     REST style controller

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {

        User user = getRepo().findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        // the name "user"  is bound to the VIEW
        model.addAttribute("user", user);
        return "update-user";
    }
    */

    @PostMapping("/edit")
    public String editUser(@RequestParam("id") long id, Model model) {

        User user = getRepo().findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        // the name "user"  is bound to the VIEW
        model.addAttribute("user", user);
        return "update-user";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            return "update-user";
        }

        getRepo().save(user);
        model.addAttribute("users", getRepo().findAll());
        return "index";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {

        User user = getRepo()
                .findById(id)
                .orElseThrow(
                    () -> new IllegalArgumentException("Invalid user Id:" + id)
                );
        getRepo().delete(user);
        model.addAttribute("users", getRepo().findAll());
        return "index";
    }

    @GetMapping(value="/json")
    public String json (Model model) {
        return "json";
    }
    /**
     * a sample controller return the content of the DB in JSON format
     * @return
     */
    @GetMapping(value="/getjson")
    public @ResponseBody List<User> getAll() {

        return getRepo().findAll();
    }
}

