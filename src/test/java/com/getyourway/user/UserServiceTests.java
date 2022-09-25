package com.getyourway.user;

import com.getyourway.Constants;
import com.getyourway.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    // ---------------- isCurrentUserOrAdmin Tests ---------------

    @Test
    @WithMockUser(authorities = {"ROLE_USER"})
    @DisplayName("givenCurrentUserIsRequestedUser_whenGetRequestedUser_thenReturnTrue")
    public void isCurrentUserTest() {

        //Given...
        Long requestedUser = Long.valueOf(1);
        User currentLoggedInUser = new User("user1", "password1");
        currentLoggedInUser.setId(Long.valueOf(1));

        given(userRepository.findByUsername("user1")).willReturn(currentLoggedInUser);

        //...when...
        boolean result = userService.isCurrentUserOrAdmin(currentLoggedInUser.getUsername(), requestedUser);

        //...asert.
        assertTrue(result);

    }

    @Test
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @DisplayName("givenCurrentUserIsAdmin_whenGetRequestedUser_thenReturnTrue")
    public void isAdminTest() {

        //Given...
        Long requestedUser = Long.valueOf(2);
        User admin = new User("admin", "password");
        admin.setId(Long.valueOf(1));
        admin.setRoles("ROLE_ADMIN");

        given(userRepository.findByUsername("admin")).willReturn(admin);

        //...when...
        boolean result = userService.isCurrentUserOrAdmin(admin.getUsername(), requestedUser);

        //...asert.
        assertTrue(result);
    }

    @Test
    @WithMockUser(authorities = {"ROLE_USER"})
    @DisplayName("givenCurrentUser_whenGetRequestedUser_thenReturnFalse")
    public void isNotCurrentUserOrAdminTest() {

        //Given...
        Long requestedUser = Long.valueOf(1);
        User user2 = new User("user2", "password2");
        user2.setId(Long.valueOf(2));

        given(userRepository.findByUsername("user2")).willReturn(user2);

        //...when...
        boolean result = userService.isCurrentUserOrAdmin(user2.getUsername(), requestedUser);

        //...asert.
        assertFalse(result);

    }

    // ---------------- getCurrentUser Tests ---------------

    @Test
    @WithMockUser(value = "testUser", password = "testPassword", authorities = {"ROLE_USER"})
    @DisplayName("givenCurrentUser_whenGetCurrentUser_returnCurrentUser")
    public void getCurrentUserTest() {

        //Given...
        User user = new User("testUser", "testPassword");
        given(userRepository.findByUsername("testUser")).willReturn(user);

        //...when...
        User currentUser = userService.getCurrentUser();

        //...asert.
        assertTrue(currentUser.equals(user));
    }

    @Test
    @WithAnonymousUser
    @DisplayName("givenCurrentUserIsAnonymous_whenGetCurrentUser_returnNull")
    public void AnonymousUserTest() {

        //Given anonymous user...
        //...when...
        User currentUser = userService.getCurrentUser();

        //...asert.
        assertNull(currentUser);

    }

    // ---------------- setRoles Tests ---------------

    @Test
    @WithMockUser(authorities = {"ROLE_ADMIN"})
    @DisplayName("givenUserIsAdmin_whenSetRoles_setRole")
    public void setRolesAsAdminTest() {

        //Given...
        User userToSet = new User("user2", "password2");
        UserDTO userDTO = new UserDTO("user2", "password2", Constants.ADMIN);

        //...when...
        userService.setRoles(userToSet, userDTO);

        //...assert.
        assertEquals(userToSet.getRoles(), userDTO.getRoles());

    }

    @Test
    @WithMockUser(authorities = {"ROLE_USER"})
    @DisplayName("givenUserIsNotAdmin_whenSetRoles_doNotSetRole")
    public void setRolesAsNonAdminTest() {

        //Given...
        User userToSet = new User("user2", "password2");
        UserDTO userDTO = new UserDTO("user2", "password2", Constants.ADMIN);

        //...when...
        userService.setRoles(userToSet, userDTO);

        //...assert.
        assertEquals(userToSet.getRoles(), Constants.USER);

    }

    @Test
    @WithMockUser(authorities = {"ROLE_Admin"})
    @DisplayName("givenUserIsAdmin_whenSetRolesNotDefined_doNotSetRole")
    public void setUndefinedRoleAsAdmin() {

        //Given...
        User userToSet = new User("user2", "password2");
        UserDTO userDTO = new UserDTO("user2", "password2", "RLE-Admn");

        //...when...
        userService.setRoles(userToSet, userDTO);

        //...assert.
        assertEquals(userToSet.getRoles(), Constants.USER);

    }

}
