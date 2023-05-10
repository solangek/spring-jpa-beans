package com.example.demo.repo;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.validation.constraints.*;
import java.io.Serializable;

@Entity
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotEmpty(message = "Name is mandatory")
    private String userName;

    @NotEmpty(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @PositiveOrZero(message = "Visits must be positive or zero")
    @NotNull
    private int visits = 0;

    public int getVisits() {
        return visits;
    }
    public void setVisits(int visits) {
        this.visits = visits;
    }

    public User() {}

    public User(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }

    public void setId(long id) {
        this.id = id;
    }
    public long getId() {
        return id;
    }

    public void setUserName(String userName) {
        // Annotation may not be enough
        // you can also perform your own validation inside setters
        // exception should be caught by service/controller
        if (userName.length() > 32)
            throw new IllegalArgumentException("Name cannot exceed 32 characters");
        this.userName = userName;
    }
    public String getUserName() {
        return userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() { return email; }


    @Override
    public String toString() {
        return "User{" + "id=" + id + ", userName=" + userName + ", email=" + email + '}';
    }
}

