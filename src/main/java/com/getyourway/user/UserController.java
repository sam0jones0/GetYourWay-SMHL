package com.getyourway.user;

import com.getyourway.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.getyourway.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LoggingSystemFactory;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    private final UserModelAssembler userAssembler;

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    public UserController(UserRepository userRepository, UserModelAssembler userAssembler) {
        this.userRepository = userRepository;
        this.userAssembler = userAssembler;
    }

    @Secured(Constants.ADMIN)
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<User>>> getUsers() {

        List<EntityModel<User>> users = new ArrayList<EntityModel<User>>();
        userRepository.findAll().forEach((user) -> users.add(userAssembler.toModel(user)));
        LOG.info("All users requested by: " + userService.getCurrentUser().getRoles());

        return ResponseEntity
                .ok()
                .body(CollectionModel.of(users, linkTo(methodOn(UserController.class).getUsers()).withSelfRel()));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@userService.isCurrentUserOrAdmin(principal.getUsername(), #id)")//principal is of type UserDetailsImpl
    public ResponseEntity<?> getThisUser(@PathVariable Long id) {

        EntityModel<User> entityModel = userAssembler.toModel(userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id)));

        return ResponseEntity
                .ok()
                .header("Location", String.valueOf(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()))
                .body(entityModel);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDto) {

        User user = new User(userDto.getUsername(), userDto.getPassword());
        userService.setRoles(user, userDto);

        EntityModel<User> entityModel = userAssembler.toModel(userRepository.save(user));
        LOG.info("New user created with id: " + entityModel.getContent().getId());

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //location response header
                .body(entityModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> replaceUser(@RequestBody User newUser, @PathVariable Long id) {

        User updatedUser = userRepository.findById(id)
                .map(user -> {
                    user.setUsername(newUser.getUsername());
                    user.setPassword(newUser.getPassword()); //TODO: This fails
                    return userRepository.save(user);
                })
                //Else create a new user
                .orElseGet(() -> {
                    newUser.setId(id);
                    return userRepository.save(newUser);
                });

        EntityModel<User> entityModel = userAssembler.toModel(updatedUser);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //location response header
                .body(entityModel);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@userService.isCurrentUserOrAdmin(principal.getUsername(), #id)")//principal is of type UserDetailsImpl
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {

        userRepository.deleteById(id);
        LOG.info("User deleted with id: " + id);

        //204 No Content Success status. Client does not need to naviagte away from current page
        return ResponseEntity.noContent().build();
    }


}
