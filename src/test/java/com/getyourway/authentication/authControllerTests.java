package com.getyourway.authentication;

import com.getyourway.Constants;
import com.getyourway.WebSecurityConfig;
import com.getyourway.repository.UserRepository;
import com.getyourway.user.User;
import com.getyourway.user.UserController;
import com.getyourway.user.UserModelAssembler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@Import(WebSecurityConfig.class)
@AutoConfigureMockMvc
@EnableWebMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("usertest")
@ExtendWith({OutputCaptureExtension.class, MockitoExtension.class, SpringExtension.class})
public class authControllerTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private AuthController authController;

    @MockBean
    private UserModelAssembler userModelAssembler;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired
    private FilterChainProxy filterChainProxy;

    private User user;


    @BeforeAll
    public void setUp() {
        user = new User("username", "password");
        user.setRoles(Constants.USER);
        user.setId(1L);

        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("givenUser_WhenLogin_SuccessfulAuthentication")
    public void loginSuccessTest(CapturedOutput output) throws Exception {

        //Given...
        Authentication authentication = mock(Authentication.class);
        authentication.setAuthenticated(true);
        given(authenticationManager.authenticate(any())).willReturn(authentication);

        given(userRepository.findByUsername(user.getUsername())).willReturn(user);

        EntityModel<User> entityModel = EntityModel.of(user,
                linkTo(methodOn(UserController.class).getThisUser(user.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).getUsers()).withRel("users"));
        given(userModelAssembler.toModel(any())).willReturn(entityModel);

        //...when...
        ResultActions response = mockMvc.perform(post("/api/auth/login")
                .param("username", user.getUsername())
                .param("password", "password")
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        String header = "$.content";
        //..assert
        response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(user.getId().intValue())))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.roles", is(user.getRoles())));

    }

    @Test
    @DisplayName("givenUser_WhenLoginWithBadPassword_UnsuccesfulLogin")
    public void loginFailurePasswordTest(CapturedOutput output) throws Exception {

        //Given...
        given(authenticationManager.authenticate(any())).willThrow(BadCredentialsException.class);
        given(userRepository.findByUsername(user.getUsername())).willReturn(user);

        //...when...
        ResultActions response = mockMvc.perform(post("/api/auth/login")
                .param("username", user.getUsername())
                .param("password", "password")
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //...assert.
        response
                .andExpect(status().isUnauthorized());

        assertTrue(output.getOut().contains("Incorrect login attempt for account id: " + user.getId()));
    }

    @Test
    @DisplayName("givenUser_WhenLoginWithBadUsername_UserNotFoundException")
    public void loginFailureUsernameTest(CapturedOutput output) throws Exception {

        //Given...
        String username = "NotAValidUser";
        given(userRepository.findByUsername(username)).willReturn(null);

        //...when...
        ResultActions response = mockMvc.perform(post("/api/auth/login")
                .param("username", username)
                .param("password", "password")
                .contentType(MediaType.APPLICATION_JSON_VALUE));

        //...assert
        response
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Could not find user " + username)));

    }

    @Test
    @DisplayName("givenAuthenticatedUser_WhenLogout_LogoutUser")
    @WithMockUser
    public void logoutSuccess(CapturedOutput output) throws Exception {

        //Given...
        Authentication authentication = mock(Authentication.class);
        authentication.setAuthenticated(true);

        given(userRepository.findByUsername(any())).willReturn(user);

        //...when...
        ResultActions response = mockMvc.perform(get("/api/auth/logout"));

        //...assert
        assertTrue(output.getOut().contains("User logged out with id: "));
        assertNull(SecurityContextHolder.getContext().getAuthentication());

    }

    @Test
    @DisplayName("givenAnonymousUser_WhenCheckAuthentication_ReturnUnauthorized")
    @WithAnonymousUser
    public void getAuthenticationAnonymousTest() throws Exception {

        //Given when...
        ResultActions response = mockMvc.perform(get("/api/auth/isUserAuthenticated"));

        //...assert
        response
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$", is("User is not logged in")));
    }

    @Test
    @DisplayName("givenAuthenticatedUser_WhenCheckAuthentication_ReturnOK")
    @WithMockUser
    public void getAuthenticationUserTest() throws Exception {

        //Given...
        EntityModel<User> entityModel = EntityModel.of(user,
                linkTo(methodOn(UserController.class).getThisUser(user.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).getUsers()).withRel("users"));

        given(userRepository.findByUsername(any())).willReturn(user);
        given(userModelAssembler.toModel(any())).willReturn(entityModel);

        // ...when...
        ResultActions response = mockMvc.perform(get("/api/auth/isUserAuthenticated"));

        //...assert
        response
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(user.getId().intValue())))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andExpect(jsonPath("$.password").doesNotExist())
                .andExpect(jsonPath("$.roles", is(user.getRoles())));

    }


}
