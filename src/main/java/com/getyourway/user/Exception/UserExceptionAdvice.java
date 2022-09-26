package com.getyourway.user.Exception;

import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.Timestamp;

@ControllerAdvice
public class UserExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public JSONObject userNotFoundHandler(UserNotFoundException exception) {

        JSONObject response = this.responseBuilder(new Timestamp(System.currentTimeMillis()), HttpStatus.NOT_FOUND.value(),
                "NOT_FOUND", exception.getMessage(), "/");

        return response;
    }

    public JSONObject responseBuilder(Timestamp timestamp, int status, String error, String message, String path) {

        //NOTE: Order is not maintained
        JSONObject response = new JSONObject();
        response.appendField("timestamp", timestamp);
        response.appendField("status", status);
        response.appendField("error", error);
        response.appendField("message", message);
        response.appendField("path", path);

        return response;

    }

}
