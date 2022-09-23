package com.getyourway.user;

import com.getyourway.repository.UserRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    private final UserModelAssembler userAssembler;

    public UserController(UserRepository userRepository, UserModelAssembler userAssembler) {
        this.userRepository = userRepository;
        this.userAssembler = userAssembler;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<User>>> getUsers() {

        List<EntityModel<User>> users = new ArrayList<EntityModel<User>>();
        userRepository.findAll().forEach((user) -> users.add(userAssembler.toModel(user)));

        //CollectionModel<EntityModel<User>> usersCollection = CollectionModel.of(users);

        return ResponseEntity
                .ok()
                .body(CollectionModel.of(users, linkTo(methodOn(UserController.class).getUsers()).withSelfRel()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getThisUser(@PathVariable Long id) {

        EntityModel<User> entityModel = userAssembler.toModel(userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id)));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //location response header
                .body(entityModel);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {

        EntityModel<User> entityModel = userAssembler.toModel(userRepository.save(user));

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //location response header
                .body(entityModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> replaceUser(@RequestBody User newUser, @PathVariable Long id) {

        User updatedUser = userRepository.findById(id)
                .map(user -> {
                    user.setUsername(newUser.getUsername());
                    user.setPassword(newUser.getPassword()); //this will fail
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
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {

        userRepository.deleteById(id);
        //204 No Content Success status. Client does not need to naviagte away from current page
        return ResponseEntity.noContent().build();
    }


}
