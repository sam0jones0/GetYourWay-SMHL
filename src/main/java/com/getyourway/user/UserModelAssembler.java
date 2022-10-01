package com.getyourway.user;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<User, EntityModel<User>> {

    /**
     * Converts the given entity (a user) into a RepresentationModel
     * so that Hypermedia can be added to the user api
     *
     * @param user The User object representing the user
     * @return EntityModel -> contains the user object as well as Hypermedia links
     *         to their unique GET api link and their parent (get all users at api/users) api link
     */
    @Override
    public EntityModel<User> toModel(User user) {

        return EntityModel.of(user,
                linkTo(methodOn(UserController.class).getThisUser(user.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).getUsers()).withRel("users"));
    }

}
