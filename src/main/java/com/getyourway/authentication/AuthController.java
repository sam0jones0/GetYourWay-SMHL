package com.getyourway.authentication;

import com.getyourway.repository.UserRepository;
import com.getyourway.user.Exception.UserNotFoundException;
import com.getyourway.user.User;
import com.getyourway.user.UserModelAssembler;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    private final UserModelAssembler userAssembler;

    private final UserRepository userRepository;

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    public AuthController(UserModelAssembler userAssembler, UserRepository userRepository) {
        this.userAssembler = userAssembler;
        this.userRepository = userRepository;
    }

    /**
     * Handles login using Spring Security's httpBasic. Attempts authentication against the User database
     * Passwords are encrypted and decrypted with bcrypt. If successful creates a new SecurityContext that
     * contains a JSESSIONID and Authentication Object. Returns the authenticated user if successful.
     * If unsuccessful returns a BadCredentialsException
     *
     * @param username The string username to be authenticated
     * @param password The string password to be authenticated (using bcrypt)
     * @param request The HTTPServletRequest containing the authentication request from client
     * @return EntityModel<User> in the body of a ResponseeEntity if succesfully authenticated
     *          If authentication is unsuccessful, returns a 401 Unauthorized ResponeEntity
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam("username") final String username, @RequestParam("password") final String password, final HttpServletRequest request) {

        //Check user exists by username
        if (userRepository.findByUsername(username) == null) {
            throw new UserNotFoundException(username);
        }

        //Create new authToken and new authentication object
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(username, password);
        Authentication auth;

        try {
            //Attempt authentication
            auth = authenticationManager.authenticate(authReq);

            //Make authentication available for all subsequent requests from client
            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(auth);

            //Get and Set Spring Security Session
            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", sc);

            //Return Entity Model (user object + uri location link)
            EntityModel<User> entityModel = userAssembler.toModel(userRepository.findByUsername(auth.getName()));
            LOG.info("User logged in with id: " + entityModel.getContent().getId());

            return ResponseEntity
                    .ok()
                    .header("Location", String.valueOf(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()))
                    .body(entityModel);

        } catch (BadCredentialsException error) {
            LOG.error("Incorrect login attempt for account id: " + userRepository.findByUsername(username).getId());

            JSONObject response = new JSONObject();
            response.appendField("message", "Incorrect password");

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(response);

        }

    }

    /**
     * Checks if a user is authenticated or not. Used for server-side session
     * checking as JSESSIONID cookies are HTTP Only
     *
     * @return EntityModel<User> if the user is authenticated. Or 401 status
     *          if user is not authorized
     */
    @GetMapping("/isUserAuthenticated")
    public ResponseEntity<?> getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof AnonymousAuthenticationToken) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not logged in");
        } else {
            User user = userRepository.findByUsername(authentication.getName());
            EntityModel<User> entityModel = userAssembler.toModel(user);

            return ResponseEntity
                    .ok()
                    .header("Location", String.valueOf(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()))
                    .body(entityModel);
        }

    }


}
