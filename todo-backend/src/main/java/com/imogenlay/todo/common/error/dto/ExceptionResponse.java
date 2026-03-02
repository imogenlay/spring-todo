package com.imogenlay.todo.common.error.dto;

import java.time.LocalDateTime; 

public record ExceptionResponse(
    String message,
    String path,
    int status,
    LocalDateTime timestamp 
) { }