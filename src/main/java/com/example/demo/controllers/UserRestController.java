package com.example.demo.controllers;

import com.example.demo.repo.UserInfo;
import com.example.demo.repo.UserInfoRepository;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserRestController {
    @Autowired
    private UserInfoRepository repository;

    /** http://localhost:8080/api/adduser
     * In this rest endpoint we handle validation errors with a custom exception handler
     * using postman try sending the following post params:
     * { "userName": "asdasd", "email": "asdasd", "visits":"2"}
     * { "userName": "", "email": "asdasd", "visits":"2"}
     * @param userInfo
     * @return
     */
    @PostMapping("/adduser")
    public UserInfo addUserRest(@Valid @RequestBody UserInfo userInfo) {
        repository.save(userInfo);
        return userInfo;
    }

    @GetMapping("/users")
    public Iterable<UserInfo> getUsers() {

        return repository.findAll();
    }

    // then comment out the exception handler below and rerun the request,
    // you will see that the response is a 400 bad request returned by spring
    // in other words, if you don't handle the validation errors, Spring will return a 400 bad request
    // writing a custom exception handler allows us to return the errors in a more user friendly way
    // and also allows us to return any other Http response, even a 200 OK response instead of a 400 bad request

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
