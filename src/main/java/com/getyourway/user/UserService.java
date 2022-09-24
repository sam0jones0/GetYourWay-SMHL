package com.getyourway.user;

import com.getyourway.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isCurrentUserOrAdmin(String username, long requestedUserId) {
        User currentUser = userRepository.findByUsername(username);
        boolean response = false;

        if (currentUser.getId() == requestedUserId || currentUser.getRoles() == "ROLE_ADMIN") {
            response = true;
        }

        return response;
    }

    public User getCurrentUser() {
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();

        if (authentication.isAuthenticated()) {
            return userRepository.findByUsername(authentication.getName());
        } else {
            return null;
        }
    }

}
