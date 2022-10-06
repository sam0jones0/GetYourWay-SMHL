package com.getyourway.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getyourway.Constants;
import com.getyourway.WebSecurityConfig;
import com.getyourway.repository.UserRepository;
import com.getyourway.user.Exception.UserExceptionAdvice;
import com.getyourway.user.Exception.UserNotFoundException;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = UserController.class)
@Import({WebSecurityConfig.class, UserExceptionAdvice.class})
@AutoConfigureMockMvc
@EnableWebMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("usertest")
@ExtendWith({OutputCaptureExtension.class, MockitoExtension.class, SpringExtension.class})

public class UserControllerTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

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

        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(username = "admin", authorities = {Constants.ADMIN})
    @DisplayName("givenAdmin_whenGetUsers_thenStatus200")
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

        given(userService.getUsers()).willReturn(users);
        //given(userRepository.findAll()).willReturn(users);
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
                .andExpect(jsonPath("$.content", hasSize(3)))
                .andExpect(jsonPath("$.links", hasSize(1)));

        String header = "$.content";
        for (int counter = 0; counter < 3; counter ++) {

            System.out.println(users.get(counter).getId().intValue());
            response
                    .andExpect(jsonPath(header +"[" + counter + "]" + ".id", is(users.get(counter).getId().intValue())))
                    .andExpect(jsonPath(header +"[" + counter + "]" + ".username", is(users.get(counter).getUsername())))
                    .andExpect(jsonPath(header +"[" + counter + "]" + ".password").doesNotExist())
                    .andExpect(jsonPath(header +"[" + counter + "]" + ".roles", is(users.get(counter).getRoles())))
                    .andExpect(jsonPath(header +"[" + counter + "]" + ".links[0].href", is(BASE_PATH + "/" + users.get(counter).getId().intValue())))
                    .andExpect(jsonPath(header +"[" + counter + "]" + ".links[1].href", is(BASE_PATH)));
        }

        assertTrue(output.getOut().contains("All users requested by: " + Constants.ADMIN));

    }

    @Test
    @WithMockUser(authorities = Constants.USER)
    @DisplayName("givenUser_whenGetUsers_thenStatus403Forbidden")
    public void testGetUsersAsNonAdmin (CapturedOutput output) throws Exception {

        //Given
        given(userService.isCurrentUserOrAdmin(user1.getUsername(), user1.getId())).willReturn(false);
        given(userService.getCurrentUser()).willReturn(user1);

        //Given when...
        ResultActions response = mockMvc
                .perform(get("/api/users"));

        //...assert.
        response
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$._embedded.userList").doesNotExist());

        assertFalse(output.getOut().contains("All users requested by: " + Constants.ADMIN));

    }

    @Test
    @WithMockUser("user1")
    @DisplayName("givenThisUser_whenGetUserWithId_thenStatus200")
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
                .andExpect(jsonPath("$.links[0].href", is(BASE_PATH + "/" + user1.getId().intValue())))
                .andExpect(jsonPath("$.links[1].href", is(BASE_PATH)));
    }

    // TODO: Pre-auth not working
    /*@Test
    @WithMockUser("user2")
    @DisplayName("givenUser_whenGetUserWithId_thenForbiddenStatus403")
    public void testGetDifferentUser() throws Exception {

        //Given...
        given(userService.isCurrentUserOrAdmin("user2", user1.getId())).willReturn(false);

        //...when...
        ResultActions response = mockMvc.perform(get("/api/users/{id}", user1.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //...assert.
        response
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.username").doesNotExist())
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.roles").doesNotExist());
    }*/

    @Test
    @WithMockUser("admin")
    @DisplayName("givenNonUser_whenGetUserWithId_thenNotFound404")
    public void testGetNonExistentUser() throws Exception {

        //Given
        long nonUser = 10L;
        given(userService.findById(nonUser)).willThrow(UserNotFoundException.class);

        //...when...
        ResultActions response = mockMvc.perform(get("/api/users/{id}", 10) //id 10 does not exist
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //...assert.
        response
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.id").doesNotExist())
                .andExpect(jsonPath("$.username").doesNotExist())
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.roles").doesNotExist());
    }

    @Test
    @DisplayName("givenAnyUser_whenCreateUser_CreateUser")
    public void testCreateUser(CapturedOutput output) throws Exception {

        //Given...
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", user1.getUsername());
        jsonObject.put("password", user1.getPassword());
        jsonObject.put("roles", Constants.ADMIN);
        jsonObject.toJSONString();

        EntityModel<User> entityModel = EntityModel.of(user1,
                linkTo(methodOn(UserController.class).getThisUser(user1.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).getUsers()).withRel("users"));

        given(userService.save(any())).willReturn(user1);
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
                .andExpect(jsonPath("$.roles", is(Constants.USER)));

        assertTrue(output.getOut().contains("New user created with id: " + user1.getId()));
    }

    @Test
    @DisplayName("givenAnyUser_whenCreateUserInvalid_BadRequest400")
    public void testInvalidUser(CapturedOutput output) throws Exception {

        //Given...
        JSONObject invalidUser = new JSONObject();
        invalidUser.put("username", "user");
        invalidUser.put("password", "pas");
        invalidUser.put("roles", Constants.ADMIN);
        invalidUser.toJSONString();

        given(userRepository.findByUsername(user1.getUsername())).willReturn(user1);
        //given

        //...when...
        ObjectMapper objectMapper = new ObjectMapper();
        ResultActions response = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(invalidUser))
                .accept(MediaType.APPLICATION_JSON_VALUE)
        );

        //...assert.
        response
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.username", is("Username must be between 5 and 20")))
                .andExpect(jsonPath("$.password", is("Password must be at least 8 characters")));

        assertFalse(output.getOut().contains("New user created with id: " + user1.getId()));

    }

    @Test
    @WithMockUser(authorities = {Constants.ADMIN})
    @DisplayName("givenAdminOrCurrentUser_whenDeleteUser_DeleteUser")
    public void testDeleteUser(CapturedOutput output) throws Exception {

        //...when...
        ResultActions response = mockMvc.perform(delete("/api/users/{id}", 1l).with(csrf()));

        //...assert
        assertTrue(output.getOut().contains("User deleted with id: " + user1.getId()));
        response.andExpect(status().isNoContent());

    }

    @Test
    @WithMockUser(authorities = Constants.ADMIN)
    @DisplayName("givenAdminOrCurrentUser_whenDeleteNonUser_DeleteUser")
    public void testDeleteUserNotFound(CapturedOutput output) throws Exception {

        //Given...
        long invalidUser = 10L;

        willThrow(UserNotFoundException.class).given(userService).deleteById(invalidUser);

        //...when...
        ResultActions response = mockMvc.perform(delete("/api/users/{id}", invalidUser));

        //...assert.
        response
                .andExpect(status().isNotFound());
        assertFalse(output.getOut().contains("User deleted with id: " + user1.getId()));
    }

    // Test Helper
    // response.andDo(MockMvcResultHandlers.print());


}
