package com.example.demo.services;

import com.example.demo.repo.User;
import com.example.demo.repo.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/** Service layer is used to implement business logic
 *  it is a good practice to separate the business logic from the controller
 *  and the repository
 *  this way the controller is only responsible for handling the request and response
 *  the repository is only responsible for the database operations
 *  and the service is responsible for the business logic
 */
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> findDuplicateUserNames(String userName) {
        List<User> users = userRepository.findByUserName(userName);
        return users;
    }


}
