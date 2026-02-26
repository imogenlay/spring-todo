package com.imogenlay.todo.category.dtos;
 
import jakarta.validation.constraints.NotBlank; 
import jakarta.validation.constraints.Size;

public record CreateCategoryDto (
    @NotBlank(message = "Name must not be blank")
    @Size(min = 2, message = "Name must be longer than 1 characters") String name
) { }