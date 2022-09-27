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
