package com.imogenlay.todo.task.dtos;

import java.time.LocalDate;

public record TaskResponse(Long id, String name, LocalDate dueDate, Boolean isArchived, String categoryName, Long categoryId) { }