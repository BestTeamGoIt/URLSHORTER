package com.bestteam.urlshorter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ItemNotFoundException extends RuntimeException {
  public static final String DEFAULT_MESSAGE = "Entity not found";

  public ItemNotFoundException() {
    super(DEFAULT_MESSAGE);
  }

  public ItemNotFoundException(String message) {
    super(message);
  }

  public ItemNotFoundException(Class<?> entityClass, Long id) {
    super(String.format("%s: {%s, id=%d}", DEFAULT_MESSAGE, entityClass.getSimpleName(), id));
  }

  public ItemNotFoundException(Class<?> entityClass, String id) {
    super(String.format("%s: {%s, id=%d}", DEFAULT_MESSAGE, entityClass.getSimpleName(), id));
  }
}
