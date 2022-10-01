package com.getyourway.authentication;

import com.getyourway.user.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {

    private static final Logger LOG = LoggerFactory.getLogger(CustomLogoutSuccessHandler.class);

    @Autowired
    private final UserDetailsServiceImpl userDetailsService;

    public CustomLogoutSuccessHandler(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Instructs Spring Security on what to do upon succesul logout. Logs the user that successfully
     * logged out and returns a Https No content stats
     *
     * @param request The HTTPServletRequest request to logout a user
     * @param response The HTTPServletResponse with status NO_CONTENT 204
     * @param authentication The Authentication object that is attempting to logout
     * @throws IOException Signals that an I/O exception of some sort has occurred
     * @throws ServletException Defines a general exception a servlet can throw when it encounters difficulty.
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        LOG.info("User logged out with id: " + userDetailsService.findUserByUsername(authentication.getName()).getId());

        super.onLogoutSuccess(request, response, authentication);
    }
}
