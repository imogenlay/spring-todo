package com.imogenlay.todo.config.factory;

import java.time.LocalDate;

import com.imogenlay.todo.category.entity.Category;

public class TaskFactoryOptions {
    
    public String name;
    public LocalDate dueDate;
    public Boolean isArchived;
    public Category category;
    
    public TaskFactoryOptions name(String name) {
        this.name = name;
        return this;
    }
    
    public TaskFactoryOptions dueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        return this;
    }
    
    public TaskFactoryOptions isArchived(Boolean isArchived) {
        this.isArchived = isArchived;
        return this;
    }

    public TaskFactoryOptions category(Category category) {
        this.category = category;
        return this;
    }
}
