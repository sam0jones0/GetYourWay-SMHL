package com.getyourway.user;

import com.getyourway.Constants;
import com.getyourway.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

        if (currentUser.getId() == requestedUserId || currentUser.getRoles() == Constants.ADMIN) {
            response = true;
        }

        return response;
    }

    public User getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication.isAuthenticated()) {
            return userRepository.findByUsername(authentication.getName());
        } else {
            return null;
        }
    }

    public void setRoles(User user, UserDTO userDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if ((authentication.isAuthenticated()) && (authentication.getAuthorities().contains(new SimpleGrantedAuthority(Constants.ADMIN)))
                && (Constants.ROLES.contains(userDto.getRoles()))) {

            user.setRoles(userDto.getRoles());

        } //TODO: throw error if current user is an admin but set role is undefined instead of just creating user.
    }
}
