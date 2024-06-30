package com.example.demo.controllers;

import com.example.demo.repo.Grade;
import com.example.demo.repo.GradeRepository;
import com.example.demo.repo.UserInfo;
import com.example.demo.repo.UserInfoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class GradeController {

    @Autowired
    private UserInfoRepository userRepository;

    @Autowired
    private GradeRepository gradeRepository;

    @GetMapping("/grade/{id}")
    public String gradeUser(Grade grade, @PathVariable long id, Model model) {
        UserInfo userInfo = userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("userId", userInfo.getId());
        return "grade-user";
    }

    // user id is passed in input type hiden form
    // we retrieve the user id from the form and save the grade
    @PostMapping("/grade")
    public String gradeUser(@Valid Grade grade, Long userId, Model model) {
        //long userId = 1;
        UserInfo userInfo = userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("Invalid user Id:" + userId));
        model.addAttribute("userId", userInfo.getId());
        grade.setUserInfo(userInfo);
        gradeRepository.save(grade);
        return "redirect:/";
    }

    // TODO
    // Add a method to handle the GET request for displaying the grades of a user
}
