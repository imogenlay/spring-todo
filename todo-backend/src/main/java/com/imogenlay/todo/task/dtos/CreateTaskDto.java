package com.imogenlay.todo.task.dtos; 
 
import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateTaskDto(
    @NotBlank(message = "Name must not be blank")
    @Size(min = 2, message = "Name must be longer than 1 characters")
    String name,

    @NotNull(message = "Due date must not be null")
    @FutureOrPresent(message = "Due date must be today or in the future")
    LocalDate dueDate,
    
    @NotNull(message = "Category ID must not be blank")
    @Min(value = 0, message = "Category ID must be at least 0")
    Long categoryId
) { } 