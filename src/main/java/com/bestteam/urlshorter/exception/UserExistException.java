package com.bestteam.urlshorter.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.function.Consumer;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
@Data
public class UserExistException {
    private String message;
    public static final String DEFAULT_MESSAGE = "User exist!";

    public UserExistException() {
        this.message = DEFAULT_MESSAGE;
    }

    public UserExistException(String message) {
        this.message = message;
    }

}
