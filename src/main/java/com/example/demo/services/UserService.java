package com.example.demo.services;

import com.example.demo.repo.UserInfo;
import com.example.demo.repo.UserInfoRepository;
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


}
