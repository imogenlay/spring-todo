package com.imogenlay.todo.common.error;

import org.springframework.http.HttpStatus;

public class InternalServerException extends RuntimeException {
    private final HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    public InternalServerException(String message) { super(message); }
    public HttpStatus getStatus() { return status; }
}
