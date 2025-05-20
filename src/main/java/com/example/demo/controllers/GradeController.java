package com.example.demo.controllers;

import com.example.demo.repo.Grade;
import com.example.demo.repo.GradeRepository;
import com.example.demo.repo.UserInfo;
import com.example.demo.services.*;
import com.example.demo.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class GradeController {

    // AGAIN for teaching purpose we use the repository directly
    // we should use the service layer
    @Autowired
    private UserService userService;

    @Autowired
    private GradeRepository gradeRepository;

    @GetMapping("/grade/{id}")
    public String gradeUser(Grade grade, @PathVariable long id, Model model) {
        UserInfo userInfo = userService.findUserById(id);
        model.addAttribute("userId", userInfo.getId());
        return "grade-user";
    }

    // user id is passed in input type hiden form
    // we save the grade
    @PostMapping("/grade")
    public String gradeUser(@Valid Grade grade, Long userId, Model model) {

        UserInfo userInfo = userService.findUserById(userId);
        model.addAttribute("userId", userInfo.getId());
        grade.setUserInfo(userInfo);
        gradeRepository.save(grade);
        return "redirect:/";
    }

    // TODO
    // Add a method to handle the GET request for displaying the grades of a user
}
