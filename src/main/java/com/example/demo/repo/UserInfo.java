package com.example.demo.repo;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.io.Serializable;
import java.util.List;

/**
 * This is an entity class that represents the user table in the database
 */
@Entity
public class UserInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty(message = "Name is mandatory")
    private String userName;

    @NotEmpty(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @Column(nullable = false)
    @PositiveOrZero(message = "Visits must be positive or zero")
    @NotNull
    private int visits = 0;

    // we define the relationship between the user and the grades
    // a user can have many grades, but a grade belongs to one user
    // if we delete a user, we want to delete all the grades associated with it
    // this is called orphan removal
    @OneToMany (mappedBy="userInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Grade> grades;

    public UserInfo() {

    }


    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getVisits() {
        return visits;
    }
    public void setVisits(int visits) {
        this.visits = visits;
    }

    public UserInfo(List<Grade> grades) {
        this.grades = grades;
    }

    public UserInfo(String userName, String email, List<Grade> grades) {
        this.userName = userName;
        this.email = email;
        this.grades = grades;
    }

    public void setId(long id) {
        this.id = id;
    }
    public long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() { return email; }


    public List<Grade> getGrades() {
        return grades;
    }

    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", userName=" + userName + ", email=" + email + '}';
    }

    /**
     * This method is used to add a grade to the user
     * @param grade the grade to add
     */
    public void setGrade(@Valid Grade grade) {
        if (grades != null) {
            grades.add(grade);
        } else {
            grades = List.of(grade);
        }
    }
}

