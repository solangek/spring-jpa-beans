package com.example.demo.services;

import com.example.demo.repo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;

/**
 * Service layer is used to implement business logic such as validation
 * of complex rules that cannot be implemented using the @Valid annotation
 */
@Service
public class ValidationService {

    /* the Validator interface is used to validate the user object
     * it is used to validate the user object in the controller
     * and it is used to validate the user object in the service layer
     * it is a good practice to use the Validator interface instead of using
     * the @Valid annotation directly in the controller
     */
    @Autowired
    private Validator validator;

    /**
     * When you need more complex validation rules that cannot be implemented using the @Valid annotation
     * you can implement custom validation at the Bean level or at the service level as shown here:
     *
     * We require that the email prefix must be equal to the user name
     * @param userInfo the user object to validate
     * @return a message if the validation fails
     */
    public String validateUser(UserInfo userInfo) {
        String message = "";

        // this is just a simple example of a cross validation rule based on the user name and email
        if (userInfo.getEmail() != null && userInfo.getUserName() != null) {
            // if email is @edu.hac.ac.il, then the user name must be equal to the email prefix
            if (userInfo.getEmail().contains("@edu.hac.ac.il")) {
                String prefix = userInfo.getEmail().split("@")[0];
                if (!prefix.equals(userInfo.getUserName())) {
                    message = "User name must be identical to email prefix (" +  prefix +
                            ") for edu.hac.ac.il addresses";
                }
            }
        }
        return message;
    }
}
