package com.imogenlay.todo.category;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.imogenlay.todo.category.dtos.CategoryResponse;
import com.imogenlay.todo.category.dtos.CreateCategoryDto;
import com.imogenlay.todo.category.dtos.UpdateCategoryDto;
import com.imogenlay.todo.category.entity.Category;
import com.imogenlay.todo.common.error.ConditionalObject;
import com.imogenlay.todo.task.TaskAccessHandler;
import com.imogenlay.todo.task.dtos.TaskResponse;

@Service
public class CategoryService {
    
    private final TaskAccessHandler taskAccessHandler;
    private final CategoryAccessHandler categoryAccessHandler;

    public CategoryService(CategoryAccessHandler categoryAccessHandler, TaskAccessHandler taskAccessHandler) {
        this.taskAccessHandler = taskAccessHandler; 
        this.categoryAccessHandler = categoryAccessHandler; 
    }

    public List<CategoryResponse> findAll(Sort sort) {
        return categoryAccessHandler.findAll(sort)
            .stream().map((c) -> c.createResponse()).toList();
    }
    
    public ConditionalObject<Category> findById(Long id) {
        return categoryAccessHandler.findById(id);
    }

    public ConditionalObject<CategoryResponse> create(CreateCategoryDto data) {
        String name = normaliseName(data.name());
        List<Category> categories = categoryAccessHandler.findDistinctWithNamesIgnoreCase(List.of(name));
        if (!categories.isEmpty())
            return new ConditionalObject<>(HttpStatus.BAD_REQUEST, "Category '" + name + "' already exists.");

        Category category = new Category();
        category.setName(name);
        category.setHue(data.hue());
        categoryAccessHandler.saveAndFlush(category);
        return new ConditionalObject<>(category.createResponse());
    }
    
    public ConditionalObject<CategoryResponse> update(Long id, UpdateCategoryDto data) { 
        ConditionalObject<Category> result = findById(id);
        if (result.hasError())
            return new ConditionalObject<>(result);

        Category category = result.getObject();
        if (data.name() != null)
            category.setName(normaliseName(data.name()));
        if (data.hue() != null)
            category.setHue(data.hue());
        categoryAccessHandler.saveAndFlush(category);
 
        return new ConditionalObject<>(category.createResponse());
    }

    public ConditionalObject<Category> delete(Long id) {
        ConditionalObject<Category> result = findById(id);
        if (result.hasError())
            return new ConditionalObject<>(result);        

        Category category = result.getObject();
        Category replacementCategory = categoryAccessHandler.findFirstCategoryThatIsNotId(id); 
        
        // Find all tasks with this category and change it to a different category.
        List<TaskResponse> tasks = taskAccessHandler.findAll(
            List.of(category.getName()),
            Sort.by(Sort.Direction.DESC, "name"));

        for (int i = 0; i < tasks.size(); i++) 
            taskAccessHandler.setCategoryOnId(tasks.get(i).id(), replacementCategory);
        
        // Delete must come last so that all tasks can have their categories changed first.
        categoryAccessHandler.delete(category); 
        return new ConditionalObject<>(category);
    }

    private String normaliseName(String name) {
        return name.toLowerCase();
    }
}
