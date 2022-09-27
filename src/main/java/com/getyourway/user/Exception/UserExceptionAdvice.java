package com.getyourway.user.Exception;

import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class UserExceptionAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public JSONObject userNotFoundHandler(UserNotFoundException exception) {

        JSONObject response = this.responseBuilder(new Timestamp(System.currentTimeMillis()), HttpStatus.NOT_FOUND.value(),
                "NOT_FOUND", exception.getMessage(), "/");

        return response;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {

        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
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
