package com.imogenlay.todo.common.error;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.imogenlay.todo.common.error.dto.ExceptionResponse;

import jakarta.servlet.http.HttpServletRequest;  

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {
        BadRequestException exception = new BadRequestException(ex.getMessage());
        return build(exception, request);
    }
     
    @ExceptionHandler(ExceptionBase.class)
    private ResponseEntity<ExceptionResponse> build(ExceptionBase ex, HttpServletRequest request) {
        return build(ex.getMessage(), ex.getStatus(), request);
    }

    private ResponseEntity<ExceptionResponse> build(String message, HttpStatus status, HttpServletRequest request) {
        String path = request.getRequestURI();
        ExceptionResponse response =
            new ExceptionResponse(
                message,
                path,
                status.value(),
                LocalDateTime.now());
        return new ResponseEntity<>(response, status);
    }
}
