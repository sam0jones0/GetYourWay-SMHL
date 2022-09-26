package com.getyourway.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getyourway.Constants;
import com.getyourway.repository.UserRepository;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.context.annotation.ImportResource;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.Assert;

import javax.swing.text.html.parser.Entity;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

//@SpringBootTest
//@WebMvcTest(UserController.class)
//@AutoConfigureMockMvc
//@ExtendWith(SpringExtension.class)

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(OutputCaptureExtension.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserModelAssembler userModelAssembler;

    @MockBean
    private UserService userService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserController userController;

    private User user1;

    private User user2;

    private User admin;

    private final String BASE_PATH = "http://localhost/api/users";

    @BeforeAll
    public void setUp() {
        user1 = new User("user1", "password1");
        user1.setId(Long.valueOf(1));
        user2 = new User("user2", "password2");
        user2.setId(Long.valueOf(2));
        admin = new User("admin", "password");
        admin.setRoles("ROLE_ADMIN");
        admin.setId(Long.valueOf(3));
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
    @DisplayName("givenAdmin_whenGetUsers_thenStatus200")
    //TODO: TestCase -> when not admin
    public void testGetUsers(CapturedOutput output) throws Exception {

        //Given...
        List<User> users = new ArrayList<User>();
        users.add(user1);
        users.add(user2);
        users.add(admin);

        EntityModel<User> entityModel1 = EntityModel.of(user1,
                linkTo(methodOn(UserController.class).getThisUser(user1.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).getUsers()).withRel("users"));

        EntityModel<User> entityModel2 = EntityModel.of(user2,
                linkTo(methodOn(UserController.class).getThisUser(user2.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).getUsers()).withRel("users"));

        EntityModel<User> entityModelAdmin = EntityModel.of(admin,
                linkTo(methodOn(UserController.class).getThisUser(admin.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).getUsers()).withRel("users"));

        given(userRepository.findAll()).willReturn(users);
        given(userService.getCurrentUser()).willReturn(admin);
        given(userModelAssembler.toModel(user1)).willReturn(entityModel1);
        given(userModelAssembler.toModel(user2)).willReturn(entityModel2);
        given(userModelAssembler.toModel(admin)).willReturn(entityModelAdmin);

        //...when...
        ResultActions response = mockMvc.perform(get("/api/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //...assert.
        response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.userList", hasSize(3)));

        String header = "$._embedded.userList";
        for (int counter = 0; counter < 3; counter ++) {

            System.out.println(users.get(counter).getId().intValue());
            response
                    .andExpect(jsonPath(header +"[" + counter + "]" + ".id", is(users.get(counter).getId().intValue())))
                    .andExpect(jsonPath(header +"[" + counter + "]" + ".username", is(users.get(counter).getUsername())))
                    .andExpect(jsonPath(header +"[" + counter + "]" + ".password").doesNotExist())
                    .andExpect(jsonPath(header +"[" + counter + "]" + ".roles", is(users.get(counter).getRoles())))
                    .andExpect(jsonPath(header +"[" + counter + "]" + "._links.self.href", is(BASE_PATH + "/" + users.get(counter).getId().intValue())))
                    .andExpect(jsonPath(header +"[" + counter + "]" + "._links.users.href", is(BASE_PATH)));
        }

        assertTrue(output.getOut().contains("All users requested by: " + Constants.ADMIN));

    }

    @Test
    @WithMockUser("user1")
    @DisplayName("givenThisUser_whenGetUserWithId_thenStatus200")
    //TODO: TestCase -> not current user or admin
    //TODO: TestCase -> user does not exist
    public void testGetThisUser() throws Exception {

        //Given...
        EntityModel<User> entityModel = EntityModel.of(user1,
                linkTo(methodOn(UserController.class).getThisUser(user1.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).getUsers()).withRel("users"));

        given(userRepository.findById(Long.valueOf(1))).willReturn(Optional.of(user1));
        given(userService.isCurrentUserOrAdmin(user1.getUsername(), user1.getId())).willReturn(true);
        given(userModelAssembler.toModel(any())).willReturn(entityModel);

        //...when...
        ResultActions response = mockMvc.perform(get("/api/users/{id}", user1.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //...assert.
        response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(user1.getId().intValue())))
                .andExpect(jsonPath("$.username", is(user1.getUsername())))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.roles", is(user1.getRoles())))
                .andExpect(jsonPath("$._links.self.href", is(BASE_PATH + "/" + user1.getId().intValue())))
                .andExpect(jsonPath("$._links.users.href", is(BASE_PATH)));
    }

    @Test
    @DisplayName("givenAnyUser_whenCreateUser_CreateUser")
    //TODO: TestCase -> invalid input
    public void testCreateUser(CapturedOutput output) throws Exception {

        //Given...
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", user1.getUsername());
        jsonObject.put("password", user1.getPassword());
        jsonObject.toJSONString();

        EntityModel<User> entityModel = EntityModel.of(user1,
                linkTo(methodOn(UserController.class).getThisUser(user1.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).getUsers()).withRel("users"));

        given(userRepository.save(any())).willReturn(user1);
        given(userModelAssembler.toModel(user1)).willReturn(entityModel);

        //...when...
        ObjectMapper objectMapper = new ObjectMapper();

        ResultActions response = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(jsonObject))
                .accept(MediaType.APPLICATION_JSON_VALUE)
        );

        //...assert.
        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(user1.getId().intValue())))
                .andExpect(jsonPath("$.username", is(user1.getUsername())))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.roles", is(Constants.USER)))
                .andExpect(jsonPath("$._links.self.href", is(BASE_PATH + "/" + user1.getId().intValue())))
                .andExpect(jsonPath("$._links.users.href", is(BASE_PATH)));

        assertTrue(output.getOut().contains("New user created with id: " + user1.getId()));
    }

    @Test
    @WithMockUser("user1")
    @DisplayName("givenAdminOrCurrentUser_whenDeleteUser_DeleteUser")
    //TODO: TestCase -> User does not exist
    public void testDeleteUser(CapturedOutput output) throws Exception {

        //Given...
        doNothing().when(userRepository).deleteById(any());
        given(userRepository.findByUsername(user1.getUsername())).willReturn(user1);
        given(userService.isCurrentUserOrAdmin(user1.getUsername(), user1.getId())).willReturn(true);

        //...when...
        ResultActions response = mockMvc.perform(delete("/api/users/{id}", user1.getId()));

        //...assert
        assertTrue(output.getOut().contains("User deleted with id: " + user1.getId()));
        response.andExpect(status().isNoContent());


    }
}
