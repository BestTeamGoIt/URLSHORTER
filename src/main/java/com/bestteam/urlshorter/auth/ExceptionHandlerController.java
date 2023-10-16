package com.bestteam.urlshorter.auth;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleException() {
        return ResponseEntity.internalServerError().build();
    }

    @ExceptionHandler({IllegalArgumentException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleIllegalArgumentException() {
        return ResponseEntity.badRequest().build();
    }
}
