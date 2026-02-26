package com.imogenlay.todo.category.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record UpdateCategoryDto(
    @Size(min = 2, message = "Name must be longer than 1 character")
    String name,

    @Min(value = 0, message = "Hue must be at least 0")
    @Max(value = 360, message = "Hue must be at most 360")
    Integer hue
) { }