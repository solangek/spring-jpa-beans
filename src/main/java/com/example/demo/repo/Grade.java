package com.example.demo.repo;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;

import java.io.Serializable;

@Entity
public class Grade implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty
    private String courseName;

    @PositiveOrZero
    @Max(100)
    private double grade;

    // you don't necessarily need a two-way relationship
    // but it's useful for querying
    @ManyToOne
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
