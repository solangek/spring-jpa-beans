package com.example.demo.controllers;

import com.example.demo.repo.UserInfo;
import com.example.demo.repo.UserInfoRepository;
import com.example.demo.services.UserService;
import com.example.demo.services.ValidationService;

import jakarta.validation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

/**
 * This is a controller class that handles the http requests
 */
@Controller
public class UserController {

    /* example: injecting a default value from the application.properties  file */
    @Value( "${demo.coursename}" )
    private String someProperty;

    // more injections of beans
    @Autowired
    private UserInfoRepository repository;
    @Autowired
    private UserService userService;
    @Autowired
    private ValidationService validationService;

    /**
     * This method is used to handle the main page
     * @param model the model object that is used to pass data to the view
     * @return the name of the view
     */
    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("course", someProperty);

        // the name "users"  is bound to the VIEW
        model.addAttribute("users", repository.findAll());
        return "index";
    }

    /**
     * This method is used to show the sign-up form
     * @param userInfo the user object that is bound to the form in thymeleaf
     *                 we're going to use this object to display errors in the
     *                 post method, since we're using the same view we must pass it
     * @return the name of the view
     */
    @GetMapping("/signup")
    public String showSignUpForm(UserInfo userInfo) {
        return "add-user";
    }

    /**
     * remember when processing a regular form submit we don't need the @RequestBody annotation,
     * conversions are done automatically by Spring
     * and we MUST use it to check for validation errors and catch them
     * @param userInfo the user object that is bound to the form
     * @param result the BindingResult object that is used to check for validation errors
     * @param model the model object that is used to pass data to the view
     * @return the name of the view
     */
    @PostMapping("/adduser")
    public String addUser(@Valid UserInfo userInfo, BindingResult result, Model model) {

        // optionally use a ValidationService class that validates more complex rules
        String err = validationService.validateUser(userInfo);
        if (!err.isEmpty()) {
            ObjectError error = new ObjectError("globalError", err);
            result.addError(error);
        }

        // in any case, validate the object and get the errors
        if (result.hasErrors()) {
            // errors MUST be displayed in the view and not just printed to the console
            System.out.println("validation errors: " + result.getAllErrors());
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

        repository.save(userInfo);

        // pass the list of users to the view
        model.addAttribute("users", repository.findAll());
        return "index";
    }

    /** we implemented a POST request for adding a user, as a result
     * if the user accesses the /adduser URL with a GET request, we must redirect him to the main page
     * @param userInfo the user object that is bound to the form
     * @param model the model object that is used to pass data to the view
     * @return the name of the view
     */
    @GetMapping("/adduser")
    public String showAddUserForm(UserInfo userInfo, Model model) {
        // redirect to main page
        return "redirect:/";
    }


    /**
     * This method is used to show the update form
     * USING GET HTTP METHODS FOR UPDATING AND DELETING IS NOT A GOOD PRACTICE
     * below you will find the same methods but using POST methods
     * @param id the id of the user to update
     * @param model the model object that is used to pass data to the view
     * @return the name of the view
     */
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {

        UserInfo userInfo = repository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Invalid user Id:" + id));

        // the name "user"  is bound to the VIEW
        model.addAttribute("userInfo", userInfo);
        return "update-user";
    }

    /** same as above but PostMapping
     * @param id the id of the user to update
     * @param model the model object that is used to pass data to the view
     * @return the name of the view
     */
    @PostMapping("/edit")
    public String editUser(Long id, Model model) {

        UserInfo userInfo = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        // the name "user"  is bound to the VIEW
        model.addAttribute("userInfo", userInfo);
        return "update-user";
    }

    /**
     * receives the user object from the form and updates it in the database
     * @param id the id of the user to update
     * @param userInfo the user object that is bound to the form
     * @param result the BindingResult object that is used to check for validation errors
     * @param model the model object that is used to pass data to the view
     * @return the name of the view
     */
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid UserInfo userInfo, BindingResult result, Model model) {
        if (result.hasErrors()) {
            userInfo.setId(id);
            System.out.println("validation errors: " + result.getAllErrors());
            return "update-user";
        }

        repository.save(userInfo);
        model.addAttribute("users", repository.findAll());
        return "index";
    }

    /**
     * shows the main page if the user tries to access the /update URL with a GET request
     * @return a redirect to the main page
     */
    @GetMapping("/update/{id}")
    public String updateUserGet() {
        return "redirect:/";
    }

    /**
     * deletes a user from the database
     * @param id the id of the user to delete
     * @param model the model object that is used to pass data to the view
     * @return the name of the view
     */
    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") long id, Model model) {

        // we throw an exception if the user is not found
        // since we don't catch the exception here, it will fall back on an error page (see below)
        UserInfo userInfo = repository
                .findById(id)
                .orElseThrow(
                    () -> new IllegalArgumentException("Invalid user Id:" + id)
                );
        repository.delete(userInfo);
        model.addAttribute("users", repository.findAll());
        return "index";
    }

    /**
     * shows the main page if the user tries to access the /delete URL with a GET request
     * @param id the id of the user to delete
     * @return a redirect to the main page
     */
    @GetMapping("/delete/{id}")
    public String deleteUserGet(@PathVariable long id) {
        return "redirect:/";
    }

    /**
     * shows the main page if the user tries to access the /delete URL with a GET request
     * @return a redirect to the main page
     */
    @GetMapping("/delete")
    public String deleteUserGetNoParams() {
        return "redirect:/";
    }

    /**
     * returns the json.html page
     * @param model the model object that is used to pass data to the view
     * @return the name of the view
     */
    @GetMapping(value="/json")
    public String json (Model model) {
        return "json";
    }
    /**
     * a sample controller return the content of the DB in JSON format
     * @return
     */

    /**
     * returns the error.html page in case of an exception
     * @return the name of the view
     */
    @GetMapping("/error")
    public String error() {
        return "error";
    }

}

