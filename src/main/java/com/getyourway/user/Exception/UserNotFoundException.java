package com.getyourway.user.Exception;

public class UserNotFoundException extends RuntimeException {

    /**
     * Exception for when a user does not exist in the
     * user repository
     *
     * @param id The long id of the user that was requested (but not found)
     */
    public UserNotFoundException(Long id) {
        super("Could not find user " + id);
    }

}
