package com.imogenlay.todo.task.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min; 
import jakarta.validation.constraints.Size;

public record UpdateTaskDto(
    @Size(min = 2, message = "Name must be longer than 1 characters")
    String name,
 
    @FutureOrPresent(message = "Due date must be today or in the future")
    LocalDate dueDate,
     
    @Min(value = 0, message = "Category ID must be at least 0")
    Long categoryId
) { }
