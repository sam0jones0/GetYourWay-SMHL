package com.getyourway.user;

import com.getyourway.repository.UserRepository;
import com.getyourway.user.User;
import com.getyourway.user.UserModelAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    private final UserModelAssembler userAssembler;

    private final UserRepository userRepository;

    public AuthController(UserModelAssembler userAssembler, UserRepository userRepository) {
        this.userAssembler = userAssembler;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam("username") final String username, @RequestParam("password") final String password, final HttpServletRequest request) {

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
            return ResponseEntity
                    .ok()
                    .header("Location", String.valueOf(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()))
                    .body(entityModel);

        } catch (BadCredentialsException error) {
            System.out.println("Temporary Log" + error); //TODO change this to a LOG

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("TODO: Decide what to place in this response body"); //TODO decide what to return as response
        }

    }

}
