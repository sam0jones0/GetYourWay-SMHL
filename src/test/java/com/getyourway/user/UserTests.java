package com.getyourway.user;

import com.getyourway.Constants;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@SpringBootTest
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserTests {

    @Autowired
    private TestEntityManager entityManager;

    private User user;

    @BeforeAll
    public void setup() {
        user = new User("username", "password");
        user.setRoles(Constants.USER);
    }

    // Create User Test
    @Test
    @DisplayName("givenInvalidUserDetails_whenSave_ThrowException")
    public void createInvalidUser() throws Exception {
        //Given...
        //...when...
        //...assert.

        new User("s", "");
    }

    // Invalid User Test

    // Save User Test

    // Test saving one to many relationship/ Assert size is what it should be

    //Getters
    @Test
    @DisplayName("givenGettersSetter_whenGetSet_CorrectResults")
    public void getterSetterTests() {

        assertEquals(user.getId(), 1);
        assertEquals(user.getUsername(), "username");
        //assertEquals(user.getPassword(), "password");
        assertEquals(user.getRoles(), Constants.USER);

        user.setUsername("newUsername");
        user.setPassword("newUsername");
        user.setRoles(Constants.ADMIN);

    }

    //Setters

}
