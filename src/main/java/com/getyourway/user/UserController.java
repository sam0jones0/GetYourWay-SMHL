package com.getyourway.user;

import com.getyourway.Constants;
import com.getyourway.user.Exception.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.getyourway.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserModelAssembler userAssembler;

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    public UserController(UserModelAssembler userAssembler) {
        this.userAssembler = userAssembler;
    }

    // GET

    /**
     * The method to GET all users from the user repository.
     * Only accessible to Admins
     *
     * @return ResponseEntity -> HTTP response containing list of user
     *         Entity Models and HTTP status code (ok)
     */
    @Secured(Constants.ADMIN)
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<User>>> getUsers() {

        List<EntityModel<User>> users = new ArrayList<EntityModel<User>>();
        userService.getUsers().forEach((user) -> users.add(userAssembler.toModel(user)));
        LOG.info("All users requested by: " + userService.getCurrentUser().getRoles());

        return ResponseEntity
                .ok()
                .body(CollectionModel.of(users, linkTo(methodOn(UserController.class).getUsers()).withSelfRel()));
    }

    /**
     * The method to GET a single user given an id
     * Only accessible to Admins or the user themselves (not other users)
     *
     * @param id The long representing unique id of user
     * @return ResponseEntity -> HTTP response containing EntityModel of requested user
     */
    @GetMapping("/{id}")
    @PreAuthorize("@userService.isCurrentUserOrAdmin(principal.getUsername(), #id)")//principal is of type UserDetailsImpl
    public ResponseEntity<?> getThisUser(@PathVariable Long id) {

        EntityModel<User> entityModel = userAssembler.toModel(userService.findById(id));

        return ResponseEntity
                .ok()
                .header("Location", String.valueOf(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()))
                .body(entityModel);
    }

    // POST

    /**
     * The method to POST a user to the controller. Creates a new user and
     * returns the created user to the client. Accesible to anyone.
     *
     * @param userDto A userDTO serialized from the request body. Represents a user
     * @return ResponseEntity -> HTTP response containing EntityModel of created user
     */
    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDto) {

        User user = new User(userDto.getUsername(), userDto.getPassword());
        userService.setRoles(user, userDto);

        EntityModel<User> entityModel = userAssembler.toModel(userService.save(user));
        LOG.info("New user created with id: " + entityModel.getContent().getId());

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //location response header
                .body(entityModel);
    }

    // PUT

    /**
     * The method to UPDATE a user's details in the repository
     * Only accessible to Admins or the user themselves (not other users)
     *
     * @param newDetailsDTO A userDTO serialized from the request body. Represents a user
     * @param id The long id for the user to be updated
     * @return ResponseEntity -> HTTP response containing EntityModel of updated user
     */
    @PutMapping("/{id}")
    @PreAuthorize("@userService.isCurrentUserOrAdmin(principal.getUsername(), #id)")//principal is of type UserDetailsImpl
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserDTO newDetailsDTO, @PathVariable long id) {

        EntityModel<User> entityModel = userAssembler.toModel(userService.updateUser(newDetailsDTO, id));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //location response header
                .body(entityModel);
    }

    // DELETE

    /**
     * The method to DELETE a user
     * Only accessible to Admins or the user themselves (not other users)
     *
     * @param id The long of the user to be deleted
     * @return ResponseEntity -> HTTP response containing just the NO_CONTENT HTTP status code
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("@userService.isCurrentUserOrAdmin(principal.getUsername(), #id)")//principal is of type UserDetailsImpl
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {

        userService.deleteById(id);
        LOG.info("User deleted with id: " + id);

        //204 No Content Success status. Client does not need to naviagte away from current page
        return ResponseEntity.noContent().build();
    }


}
