package com.getyourway.user;

import com.getyourway.user.Exception.UserExceptionAdvice;
import com.getyourway.user.Exception.UserNotFoundException;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = UserExceptionAdvice.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
public class UserExceptionAdviceTests {

    @Autowired
    UserExceptionAdvice userExceptionAdvice;

    @Test
    public void UserNotFoundHandlerTest() throws Exception {

        UserNotFoundException userNotFoundException = new UserNotFoundException(1L);

        JSONObject response = userExceptionAdvice.userNotFoundHandler(userNotFoundException);

        assertEquals(response.get("status"), HttpStatus.NOT_FOUND.value());
        assertEquals(response.get("error"), "NOT_FOUND");
        assertEquals(response.get("message"), userNotFoundException.getMessage());

    }

    @Test
    public void handleMethodArgumentNotValidTest() throws Exception {


    }

    @Test
    @DisplayName("Test1")
    public void responseBuilder() throws Exception {

    }
}
