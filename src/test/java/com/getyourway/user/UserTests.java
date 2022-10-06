package com.getyourway.user;

import com.getyourway.Constants;
import com.getyourway.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("usertest")
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeAll
    public void setup() {
        user = new User("username", "password");
        user.setRoles(Constants.USER);
    }

    @Test
    @DisplayName("givenInvalidUserDetails_whenSave_ThrowException")
    public void createInvalidUser() throws Exception {

        User userTooSmall = new User("usn", "psw");
        User userTooBig = new User("username123456789123456789", "password");
        User userNotUnique = new User("username", "password");

        // Username and Password cannot be null
        assertThrows(IllegalArgumentException.class,() -> new User(null, null) );

        // Username and Password too small
        userRepository.save(userTooSmall);
        assertThrows(ConstraintViolationException.class,() -> entityManager.flush() );
        entityManager.clear();

        // Username too big
        userRepository.save(userTooBig);
        assertThrows(ConstraintViolationException.class,() -> entityManager.flush() );
        entityManager.clear();

        //User not unique
        userRepository.save(user);
        userRepository.save(userNotUnique);
        entityManager.merge(user);
        entityManager.merge(userNotUnique);
        assertThrows(PersistenceException.class,() -> entityManager.flush() );
        entityManager.clear();

    }

    @Test
    @DisplayName("givenGettersSetter_whenGetSet_CorrectResults")
    public void getterSetterTests() {

        userRepository.save(user);

        // Getter Tests
        assertEquals(user.getId(), 1);
        assertEquals(user.getUsername(), "username");
        assertTrue((User.PASSWORD_ENCODER.matches("password", user.getPassword())));
        assertEquals(user.getRoles(), Constants.USER);

        // Setter Test
        user.setUsername("newUsername");
        user.setPassword("newPassword");
        user.setRoles(Constants.ADMIN);

        assertEquals(user.getId(), 1);
        assertEquals(user.getUsername(), "newUsername");
        assertTrue((User.PASSWORD_ENCODER.matches("newPassword", user.getPassword())));
        assertEquals(user.getRoles(), Constants.ADMIN);

    }

    @Test
    @DisplayName("givenPlaintextPassword_whenCreateUser_PasswordIsEncoded")
    public void passwordEncodingTest() {

        User user2 = new User("username", "plainTextPassword");
        assertTrue(user2.getPassword().matches("^\\$2a\\$.*$")); //bcrypt head is $2a$

    }

    @Test
    @DisplayName("givenUserObject_whenSecondUserobject_BothAreEqual")
    public void equalsTests() {

        User secondUser = new User(user.getUsername(), "password");
        secondUser.setRoles(user.getRoles());
        secondUser.setId(user.getId());

        assertTrue(user.equals(secondUser));

    }

    @Test
    @DisplayName("givenUser_WhenSaveUser_IDAutoGenerated")
    public void idTest() {

        User secondUser = new User("username2", "password2");
        User savedUser = userRepository.save(secondUser);
        assertNotNull(savedUser.getId());
    }

    @Test
    @DisplayName("givenUsername_WhenFindByUsername_ReturnCorrectUser")
    public void findByUsernameTest() {

        entityManager.merge(user);

        User returned = userRepository.findByUsername(user.getUsername());

        assertNotNull(returned);
        assertEquals(returned.getUsername(), user.getUsername());
        assertEquals(returned.getRoles(), user.getRoles());

    }

}
