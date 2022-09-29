package com.getyourway.user;

import com.getyourway.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Located the current user from the repository based on their username
     *
     * @param username the username identifying the user whose data is required.
     * @return UserDetailsImpl which implements a UserDetails
     * @throws UsernameNotFoundException Thrown if an UserDetailsService implementation cannot locate a User by its username.
     */
    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }

        return new UserDetailsImpl(user);
    }

    /**
     * Located the current user from the repository based on their username
     *
     * @param username The string username identifying the user whose data is required.
     * @return The user object if they exist
     * @throws UsernameNotFoundException Thrown if an UserDetailsService implementation cannot locate a User by its username.
     */
    public User findUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }

        return user;
    }
}
