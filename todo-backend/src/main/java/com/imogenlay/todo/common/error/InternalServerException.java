package com.imogenlay.todo.common.error;

import org.springframework.http.HttpStatus;

public class InternalServerException extends ExceptionBase {
    public InternalServerException(String message) { super(HttpStatus.INTERNAL_SERVER_ERROR, message); }
}
