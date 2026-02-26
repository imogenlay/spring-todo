package com.imogenlay.todo.category.dtos;
 
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateCategoryDto(
    @NotBlank(message = "Name must not be blank")
    @Size(min = 2, message = "Name must be longer than 1 characters") String name,
    
    @NotNull(message = "Hue is required")
    @Size(min = 0, max = 360, message = "Hue must be between 0 and 360") Short hue

) { }