package com.example.demo.controllers;

import com.example.demo.repo.User;
import com.example.demo.repo.UserRepository;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    /* example: injecting a default value from the application.properties  file */
    @Value( "${demo.coursename}" )
    private String someProperty;

    /* inject via its type the User repo bean - a singleton */
    @Autowired
    private UserRepository repository;

    @GetMapping("/")
    public String main(User user, Model model) {
        model.addAttribute("course", someProperty);

        // the name "users"  is bound to the VIEW
        model.addAttribute("users", repository.findAll());
        return "index";
    }

    @GetMapping("/signup")
    public String showSignUpForm(User user, Model model) {
        //model.addAttribute("user", new User("noname","noemail"));
        return "add-user";
    }

    /**
     * because we declared a BindingResult parameter, Spring will pass it to the controller
     * and we MUST use it to check for validation errors and catch them
     * @param user
     * @param result
     * @param model
     * @return
     */
    @PostMapping("/adduser")
    public String addUser(@Valid User user, BindingResult result, Model model) {

        // validate the object and get the errors
        if (result.hasErrors()) {
            // errors will be displayed in the view
            return "add-user";
        }

        // you can also validate the object manually:

//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        Validator validator = factory.getValidator();
//        Set<ConstraintViolation<User>> violations = validator.validate(user);
//        for (ConstraintViolation<User> violation : violations) {
//            System.out.println(violation.getMessage());
//        }

        // if you don't deal with validation errors, the user will try to be added
        // to the database even if it's not valid, then you'll get an exception
        // when you try to save it to the database

        repository.save(user);

        // pass the list of users to the view
        model.addAttribute("users", repository.findAll());
        return "index";
    }

    /** we implemented a POST request for adding a user, as a result
     * if the user accesses the /adduser URL with a GET request, we must redirect him to the main page
     * @param user
     * @param model
     * @return
     */
    @GetMapping("/adduser")
    public String showAddUserForm(User user, Model model) {
        // redirect to main page
        return "redirect:/";
    }


    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {

        User user = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        // the name "user"  is bound to the VIEW
        model.addAttribute("user", user);
        return "update-user";
    }

    /* same as above but PostMapping
    @PostMapping("/edit")
    public String editUser(@RequestParam("id") long id, Model model) {

        User user = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        // the name "user"  is bound to the VIEW
        model.addAttribute("user", user);
        return "update-user";
    }    */

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            return "update-user";
        }

        repository.save(user);
        model.addAttribute("users", repository.findAll());
        return "index";
    }

    @GetMapping("/update/{id}")
    public String updateUserGet() {
        return "redirect:/";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") long id, Model model) {

        User user = repository
                .findById(id)
                .orElseThrow(
                    () -> new IllegalArgumentException("Invalid user Id:" + id)
                );
        repository.delete(user);
        model.addAttribute("users", repository.findAll());
        return "index";
    }

    @GetMapping("/delete/{id}")
    public String deleteUserGet() {
        return "redirect:/";
    }

    /**
     * returns the json.html page
     * @param model
     * @return
     */
    @GetMapping(value="/json")
    public String json (Model model) {
        return "json";
    }
    /**
     * a sample controller return the content of the DB in JSON format
     * @return
     */

    /** handling IllegalArgumentException */
    @GetMapping("/error")
    public String error() {
        return "error";
    }


}

