package com.getyourway.user;

import com.getyourway.Constants;
import com.getyourway.repository.UserRepository;
import com.getyourway.user.Exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Iterable<User> getUsers() {
        return userRepository.findAll();
    }

    /**
     * Finds a user from the user repository by id
     *
     * @param id The long (unique) representing the user
     * @return Optional User -> the requested user or throws an exception if not found
     */
    public User findById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    /**
     * Saves a user to the user repository
     *
     * @param user The User entity representing the user to save
     * @return The saved user
     */
    public User save(User user) {
        return userRepository.save(user);
    }

    /**
     * Updates a given user's details (field attributes)
     *
     * @param newDetailsDTO A UserDTO containing the details to be updated
     * @param id The long (unique) representing the user
     * @return userToUpdate -> The updated user object
     */
    public User updateUser(UserDTO newDetailsDTO, long id) {

        User newDetails = new User(newDetailsDTO.getUsername(), newDetailsDTO.getPassword());
        this.setRoles(newDetails, newDetailsDTO);

        User userToUpdate = userRepository.findById(id)
                .map(user -> {
                    user.setUsername(newDetails.getUsername());
                    user.setPassword(newDetails.getPassword());
                    return userRepository.save(user);
                })
                //Else create a new user
                .orElseThrow(() -> new UserNotFoundException(id));

        return userToUpdate;

    }

    /**
     * Deletes the given user. If user not found then throws
     * the UsernNotFoundException
     *
     * @param id The long (unique) representing the user
     */
    public void deleteById(long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        userRepository.deleteById(id);
    }

    /**
     * Checks if the current authenticated user from Spring security's principal
     * is an admin or the correct user. A user should only be able to perform HTTP
     * Requests on themselves and no other users. Admins can perform HTTP Requests
     * on any user.
     *
     * @param username The String username of the current logged in user
     * @param requestedUserId The long (unique) representing the user that the current logged in user
     *                         is trying to access
     * @return response -> boolean, true if the current user is authorized to access the requested user
     */
    public boolean isCurrentUserOrAdmin(String username, long requestedUserId) {
        User currentUser = userRepository.findByUsername(username);
        boolean response = false;

        if (currentUser.getId() == requestedUserId || currentUser.getRoles() == Constants.ADMIN) {
            response = true;
        }

        return response;
    }

    /**
     * Gets the current user for this session from Spring Security's SecurityContext
     *
     * @return User -> the User object representing the current logged in User if there is one
     */
    public User getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.isAuthenticated()) {
            return userRepository.findByUsername(authentication.getName());
        } else {
            return null;
        }
    }

    /**
     * Checks if the current logged in user has authority (admin) to change a user's default
     * authority (ROLE_USER). Also checks that the new suthority is a valid authority.
     * If current user is not an Admin, will set the authority of the requested user to the defualt (ROLE_ADMIN)
     *
     * @param user The User whose authority needs to be set
     * @param userDto The UserDTO of the user whose authority needs to be set
     */
    public void setRoles(User user, UserDTO userDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if ((authentication.isAuthenticated()) && (authentication.getAuthorities().contains(new SimpleGrantedAuthority(Constants.ADMIN)))
                && (Constants.ROLES.contains(userDto.getRoles()))) {

            user.setRoles(userDto.getRoles());

        }
    }

    /**
     * Checks if a user exists in the user repository
     *
     * @param id The long of the user to be checked
     * @return boolean - true if user exists, false if they do not exist
     */
    public boolean exitsById(long id) {
        return userRepository.existsById(id);
    }
}
