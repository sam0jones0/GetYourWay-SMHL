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

import java.sql.Timestamp;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = UserExceptionAdvice.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
public class UserExceptionAdviceTests {

    @Autowired
    UserExceptionAdvice userExceptionAdvice;

    @Test
    @DisplayName("givenUserNotFound_WhenExceptionHandler_ReturnResponseObject")
    public void UserNotFoundHandlerTest() throws Exception {

        UserNotFoundException userNotFoundException = new UserNotFoundException(1L);

        JSONObject response = userExceptionAdvice.userNotFoundHandler(userNotFoundException);

        assertEquals(response.get("status"), HttpStatus.NOT_FOUND.value());
        assertEquals(response.get("error"), "NOT_FOUND");
        assertEquals(response.get("message"), userNotFoundException.getMessage());

    }

    @Test
    @DisplayName("givenErrorDetails_WhenResponseBuilder_ReturnJSONObject")
    public void responseBuilderTest() throws Exception {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        int status = HttpStatus.BAD_REQUEST.value();
        String error = "BAD_REQUEST";
        String message = "Some error message";
        String path = "/";

        JSONObject response = userExceptionAdvice.responseBuilder(timestamp, status, error, message, path);

        assertEquals(response.get("timestamp"), timestamp);
        assertEquals(response.get("status"), status);
        assertEquals(response.get("error"), error);
        assertEquals(response.get("message"), message);
        assertEquals(response.get("path"), path);

    }

}
