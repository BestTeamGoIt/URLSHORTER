package com.bestteam.urlshorter.exception;

import com.bestteam.urlshorter.models.UserUrl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.function.Consumer;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserExistException extends RuntimeException {
    public static final String DEFAULT_MESSAGE = "User exist!";

    public UserExistException() {
        super(DEFAULT_MESSAGE);
    }

    public UserExistException(String message) {
        super(message);
    }

}
