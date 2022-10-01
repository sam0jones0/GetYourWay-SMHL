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

    /**
     * Handles the Http response when a user is not found
     * in the repository.
     *
     * @param exception The userNotFoundException that triggered this handler
     * @return response -> A HTTP response containing the HTTP status and error
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public JSONObject userNotFoundHandler(UserNotFoundException exception) {

        JSONObject response = this.responseBuilder(new Timestamp(System.currentTimeMillis()), HttpStatus.NOT_FOUND.value(),
                "NOT_FOUND", exception.getMessage(), "/");

        return response;
    }

    /**
     * Handles the errors for an entity with invalid fields
     * returns a list of incorrect field names and the constraints
     * that they failed to meet
     *
     * @param exception The MethodArgumentNotValidException that triggered this handler
     * @return errors -> A list of erors detailing which user inputs were invalid
     */
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

    /**
     * Builds a JSONObject that is added to the body of a HTTP response
     * containing information about the exception
     *
     * @param timestamp The time the exception occurred
     * @param status The HTTP status number of the exception
     * @param error The HTTP status string name
     * @param message The message from the exception detailing what caused the exception
     * @param path The path that the exception occurred
     * @return response -> A JSONObject containing the details of an error
     */
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
