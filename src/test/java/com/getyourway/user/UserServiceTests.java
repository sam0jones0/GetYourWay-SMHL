package com.getyourway.user;

import com.getyourway.Constants;
import com.getyourway.repository.UserRepository;
import com.getyourway.user.Exception.UserNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest(classes = UserService.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
public class UserServiceTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private List<User> allUsers;
    private User user3;

//    @BeforeAll
//    public void setUp() {
//        allUsers = new ArrayList<>();
//        User user1 = new User("user1", "password1");
//        user1.setId(Long.valueOf(1));
//        allUsers.add(user1);
//        User user2 = new User("user2", "password2");
//        user2.setId(Long.valueOf(2));
//        allUsers.add(user2);
//        User admin = new User("admin", "password");
//        admin.setRoles("ROLE_ADMIN");
//        admin.setId(Long.valueOf(3));
//        allUsers.add(admin);
//
//        user3 = new User("user3", "password3");
//        user3.setId(Long.valueOf(4));
//    }

    // ---------------- User CRUD Tests ---------------

//    @Test
//    @DisplayName("whenGetUsers_thenReturnUsersList")
//    public void getUsersTest() {
//
//        //Given...
//        given(userRepository.findAll()).willReturn(allUsers);
//
//        //...when...
//        Iterable result = userService.getUsers();
//
//        //...assert
//        assertNotNull(result);
//        assertEquals(3, allUsers.size());
//        assertEquals(result, allUsers);
//    }

//    @Test
//    @DisplayName("givenId_whenGetUsers_thenReturnUser")
//    public void findByIdTestSuccess() {
//
//        //Given...
//        int id = user3.getId().intValue();
//        given(userRepository.findById((Long.valueOf(4)))).willReturn(Optional.of(user3));
//
//        //...when...
//        User result = userService.findById(id);
//
//        //...assert
//        assertNotNull(result);
//        assertTrue(result.equals(user3));
//    }

//    @Test
//    @DisplayName("givenInvalidId_whenGetUsers_thenThrowException")
//    public void findByIdTestFailure() {
//
//        //Given...
//        int id = user3.getId().intValue();
//        given(userRepository.findById((Long.valueOf(4)))).willThrow(UserNotFoundException.class);
//
//        //...when assert
//        assertThrows(UserNotFoundException.class, ()->userService.findById(id));
//    }

//    @Test
//    @DisplayName("givenUser_whenSaveUser_thenSaveAndReturnUser")
//    public void saveTest() {
//
//        //Given...
//        given(userRepository.save(user3)).willReturn(user3);
//
//        //...when...
//        User result = userService.save(user3);
//
//        //...assert
//        assertNotNull(user3);
//        assertTrue(result.equals(user3));
//
//    }

//    @Test
//    @WithMockUser(authorities = {"ROLE_USER"})
//    @DisplayName("givenValidNewUserDetails_whenUpdateUser_thenUpdateUser")
//    public void updateUserSuccessTest() {
//
//        //Given...
//        UserDTO user3Updates = new UserDTO("newUsername", "newPassword", Constants.USER);
//        given(userRepository.findById((Long.valueOf(4)))).willReturn(Optional.of(user3));
//        given(userRepository.save(any(User.class))).willAnswer(invocation ->
//                invocation.getArgument(0, User.class));
//
//        //...when...
//        User result = userService.updateUser(user3Updates, user3.getId());
//
//        //...assert.
//        assertNotNull(result);
//        assertEquals(user3.getId(), result.getId());
//        assertEquals(result.getUsername(), user3Updates.getUsername());
//        //assertEquals(result.getUsername(), user3.getUsername()); //TODO: user3 = updates = result?
//        //assertTrue(User.PASSWORD_ENCODER.matches(user3.getPassword(), result.getPassword())); //TODO: This fails
//        //assertEquals(user3.getPassword(), result.getPassword()); //TODO: this is the same when it shouldn't be!
//        assertEquals(result.getRoles(), user3Updates.getRoles());
//
//    }

//    @Test
//    @WithMockUser(authorities = {"ROLE_USER"})
//    @DisplayName("givenUserNotFound_whenUpdateUser_thenThrowException")
//    public void updateUserFailureNotFoundTest() {
//
//        //Given...
//        UserDTO user3Updates = new UserDTO("newUsername", "newPassword", Constants.USER);
//        long id = 10; //not in list
//        given(userRepository.findById(id)).willThrow(UserNotFoundException.class);
//
//        //...when assert
//        assertThrows(UserNotFoundException.class, () -> userService.updateUser(user3Updates, id));
//
//    }

//    @Test
//    @DisplayName("givenId_whenDeleteUser_thenDeleteUser")
//    public void deleteByIdTestSuccess() {
//
//        //Given...
//        long id = user3.getId();
//        given(userRepository.findById(id)).willReturn(Optional.of(user3));
//
//        //...when assert
//        userService.deleteById(id);
//        verify(userRepository).deleteById(any());
//
//    }

//    @Test
//    @DisplayName("givenInvalidId_whenDeleteUser_thenThrowException")
//    public void deleteByIdTestFailure() {
//
//        //Given...
//        long id = user3.getId() + 1;
//        given(userRepository.findById((Long.valueOf(id)))).willThrow(UserNotFoundException.class);
//
//        //...when assert
//        assertThrows(UserNotFoundException.class, ()->userService.deleteById(id));
//    }

    // ---------------- isCurrentUserOrAdmin Tests ---------------

//    @Test
//    @WithMockUser(authorities = {"ROLE_USER"})
//    @DisplayName("givenCurrentUserIsRequestedUser_whenGetRequestedUser_thenReturnTrue")
//    public void isCurrentUserTest() {
//
//        //Given...
//        Long requestedUser = Long.valueOf(1);
//        User currentLoggedInUser = new User("user1", "password1");
//        currentLoggedInUser.setId(Long.valueOf(1));
//
//        given(userRepository.findByUsername("user1")).willReturn(currentLoggedInUser);
//
//        //...when...
//        boolean result = userService.isCurrentUserOrAdmin(currentLoggedInUser.getUsername(), requestedUser);
//
//        //...assert.
//        assertTrue(result);
//
//    }

//    @Test
//    @WithMockUser(authorities = {"ROLE_ADMIN"})
//    @DisplayName("givenCurrentUserIsAdmin_whenGetRequestedUser_thenReturnTrue")
//    public void isAdminTest() {
//
//        //Given...
//        Long requestedUser = Long.valueOf(2);
//        User admin = new User("admin", "password");
//        admin.setId(Long.valueOf(1));
//        admin.setRoles("ROLE_ADMIN");
//
//        given(userRepository.findByUsername("admin")).willReturn(admin);
//
//        //...when...
//        boolean result = userService.isCurrentUserOrAdmin(admin.getUsername(), requestedUser);
//
//        //...assert.
//        assertTrue(result);
//    }

//    @Test
//    @WithMockUser(authorities = {"ROLE_USER"})
//    @DisplayName("givenCurrentUser_whenGetRequestedUser_thenReturnFalse")
//    public void isNotCurrentUserOrAdminTest() {
//
//        //Given...
//        Long requestedUser = Long.valueOf(1);
//        User user2 = new User("user2", "password2");
//        user2.setId(Long.valueOf(2));
//
//        given(userRepository.findByUsername("user2")).willReturn(user2);
//
//        //...when...
//        boolean result = userService.isCurrentUserOrAdmin(user2.getUsername(), requestedUser);
//
//        //...assert.
//        assertFalse(result);
//
//    }

    // ---------------- getCurrentUser Tests ---------------

//    @Test
//    @WithMockUser(value = "testUser", password = "testPassword", authorities = {"ROLE_USER"})
//    @DisplayName("givenCurrentUser_whenGetCurrentUser_returnCurrentUser")
//    public void getCurrentUserTest() {
//
//        //Given...
//        User user = new User("testUser", "testPassword");
//        given(userRepository.findByUsername("testUser")).willReturn(user);
//
//        //...when...
//        User currentUser = userService.getCurrentUser();
//
//        //...assert.
//        assertTrue(currentUser.equals(user));
//    }

//    @Test
//    @WithAnonymousUser
//    @DisplayName("givenCurrentUserIsAnonymous_whenGetCurrentUser_returnNull")
//    public void AnonymousUserTest() {
//
//        //Given anonymous user...
//        //...when...
//        User currentUser = userService.getCurrentUser();
//
//        //...assert.
//        assertNull(currentUser);
//
//    }

    // ---------------- setRoles Tests ---------------

//    @Test
//    @WithMockUser(authorities = {"ROLE_ADMIN"})
//    @DisplayName("givenUserIsAdmin_whenSetRoles_setRole")
//    public void setRolesAsAdminTest() {
//
//        //Given...
//        User userToSet = new User("user2", "password2");
//        UserDTO userDTO = new UserDTO("user2", "password2", Constants.ADMIN);
//
//        //...when...
//        userService.setRoles(userToSet, userDTO);
//
//        //...assert.
//        assertEquals(userToSet.getRoles(), userDTO.getRoles());
//
//    }

//    @Test
//    @WithMockUser(authorities = {"ROLE_USER"})
//    @DisplayName("givenUserIsNotAdmin_whenSetRoles_doNotSetRole")
//    public void setRolesAsNonAdminTest() {
//
//        //Given...
//        User userToSet = new User("user2", "password2");
//        UserDTO userDTO = new UserDTO("user2", "password2", Constants.ADMIN);
//
//        //...when...
//        userService.setRoles(userToSet, userDTO);
//
//        //...assert.
//        assertEquals(userToSet.getRoles(), Constants.USER);
//
//    }

//    @Test
//    @WithMockUser(authorities = {"ROLE_Admin"})
//    @DisplayName("givenUserIsAdmin_whenSetRolesNotDefined_doNotSetRole")
//    public void setUndefinedRoleAsAdmin() {
//
//        //Given...
//        User userToSet = new User("user2", "password2");
//        UserDTO userDTO = new UserDTO("user2", "password2", "RLE-Admn");
//
//        //...when...
//        userService.setRoles(userToSet, userDTO);
//
//        //...assert.
//        assertEquals(userToSet.getRoles(), Constants.USER);
//
//    }

}
