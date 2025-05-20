package com.example.demo.services;

import com.example.demo.repo.UserInfo;
import com.example.demo.repo.UserInfoRepository;
import jakarta.validation.Valid;
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
    private UserInfoRepository userRepository;

    /**
     * This method is used to find all the users in the database
     * @param userName the user name to search for
     * @return a list of users with the same user name
     */
    public List<UserInfo> findDuplicateUserNames(String userName) {
        List<UserInfo> users = userRepository.findByUserName(userName);
        return users;
    }

    public List<UserInfo> getAllUsers() {
        return userRepository.findAll();
        // findAll does not return the grades
    }

    /**
     * This method is used to save the user in the database
     * @param userInfo the user object to save
     * @return the saved user object
     */
    public UserInfo saveUser(@Valid UserInfo userInfo) {
        return userRepository.save(userInfo);
    }

    /**
     * This method is used to find a user by id
     * @param id the id of the user to find
     * @return the user object with the given id
     */
    public UserInfo findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Invalid user Id:" + id));
    }

    /**
     * This method is used to delete a user by id
     * @param id the id of the user to delete
     */
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
        // if user is not found, it will throw an exception
    }

    /**
     * This method is used to update a user in the database
     * @param userInfo the user object to update
     */
    public void updateUser(UserInfo userInfo) {
        // we retrieve the user from the database
        UserInfo user = userRepository.findById(userInfo.getId()).orElseThrow(
                () -> new IllegalArgumentException("Invalid user Id:" + userInfo.getId()));
        // we update the user object with the new values
        // it is important to use the same original object to update the database
        user.setUserName(userInfo.getUserName());
        user.setEmail(userInfo.getEmail());
        user.setVisits(userInfo.getVisits());
        userRepository.save(userInfo);
    }


}
