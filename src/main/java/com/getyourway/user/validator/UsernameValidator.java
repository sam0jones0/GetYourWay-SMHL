package com.getyourway.user.validator;

import com.getyourway.repository.UserRepository;
import com.getyourway.user.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<UsernameConstraint, String> {

    private UserRepository userRepository;

    public UsernameValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(UsernameConstraint usernameConstraint) {
    }

    /**
     * The validation implementation from the ConstraintValidator class
     * Determines if an entity field is valid or invalid
     *
     * @param usernameField The string field that represents the username of a user
     * @param cxt ConstraintValidatorcontext
     * @return usernameIsValid -> boolean, true if username is valid
     */
    @Override
    public boolean isValid(String usernameField, ConstraintValidatorContext cxt) {

        boolean usernameIsValid = true;

        try {
            User user = userRepository.findByUsername(usernameField);
            usernameIsValid = (user == null)? true : false;
        } catch (Exception e) {
            //Do nothing
        }

        return usernameIsValid;

    }


}
