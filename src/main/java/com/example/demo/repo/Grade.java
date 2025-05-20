package com.example.demo.repo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.io.Serializable;

/**
 * The goal of this bean is to demonstrate relationships between entities
 * A grade is a course grade that is associated with a user, therefore
 * it has a many-to-one relationship with the UserInfo entity and/or
 * a one-to-many relationship in the UserInfo entity. Not both are necessary
 * but it's useful for querying: getting the user's grades or getting the user of a grade.
 */
@Entity
public class Grade implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @NotEmpty(message = "course name is mandatory")
    private String courseName;

    @PositiveOrZero
    @Max(100)
    private double grade;

    // you don't necessarily need a two-way relationship
    // but it's useful for querying
    @ManyToOne
    //@JoinColumn(name = "user_info_id")
    private UserInfo userInfo;

    public Grade() {
        grade = 0;
    }

    public Grade(String courseName, double grade) {
        this.courseName = courseName;
        this.grade = grade;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "id=" + id +
                ", courseName='" + courseName + '\'' +
                ", grade=" + grade +
                '}';
    }
}
