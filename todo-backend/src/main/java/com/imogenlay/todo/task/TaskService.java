package com.imogenlay.todo.task;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.imogenlay.todo.category.CategoryAccessHandler; 
import com.imogenlay.todo.category.entity.Category;
import com.imogenlay.todo.common.error.ConditionalObject;
import com.imogenlay.todo.task.dtos.CreateTaskDto;
import com.imogenlay.todo.task.dtos.TaskResponse;
import com.imogenlay.todo.task.dtos.UpdateTaskDto;
import com.imogenlay.todo.task.entity.Task;

@Service
public class TaskService {
    
    private final TaskAccessHandler taskAccessHandler;
    private final CategoryAccessHandler categoryAccessHandler;

    public TaskService(CategoryAccessHandler categoryAccessHandler, TaskAccessHandler taskAccessHandler) {
        this.taskAccessHandler = taskAccessHandler; 
        this.categoryAccessHandler = categoryAccessHandler; 
    }

    public List<TaskResponse> findAll(List<String> categories, Sort sort) {
        return taskAccessHandler.findAll(categories, sort);
    }    
    
    public ConditionalObject<Task> findById(Long id) {
        Optional<Task> result = taskAccessHandler.findById(id);
        if (result.isEmpty())
            return new ConditionalObject<>(HttpStatus.NOT_FOUND, "Task with ID [" + id + "] does not exist.");

        return new ConditionalObject<>(result.get());
    }

    public ConditionalObject<TaskResponse> create(CreateTaskDto data) { 
        ConditionalObject<Category> result = categoryAccessHandler.findById(data.categoryId());
        if (result.hasError())
            return new ConditionalObject<>(result);
 
        Task task = new Task();
        task.setName(data.name());
        task.setDueDate(data.dueDate());
        task.setIsArchived(false);
        task.setCategory(result.getObject());
        taskAccessHandler.saveAndFlush(task);
        return new ConditionalObject<>(task.createResponse());
    }
    
    public ConditionalObject<TaskResponse> update(Long id, UpdateTaskDto data) { 
        ConditionalObject<Task> resultTask = findById(id);
        if (resultTask.hasError())
            return new ConditionalObject<>(resultTask);

        Task task = resultTask.getObject();
        if (data.categoryId() != null)
        {
            ConditionalObject<Category> resultCategory = categoryAccessHandler.findById(id);
            if (resultCategory.hasError())
                return new ConditionalObject<>(resultCategory);
            task.setCategory(resultCategory.getObject());
        }

        if (data.name() != null)
            task.setName(data.name());
        if (data.dueDate() != null)
            task.setDueDate(data.dueDate()); 
       
        taskAccessHandler.saveAndFlush(task);
        return new ConditionalObject<>(task.createResponse());
    }

    public ConditionalObject<TaskResponse> delete(Long id) {
        ConditionalObject<Task> result = findById(id);
        if (result.hasError())
            return new ConditionalObject<>(result);
        
        Task task = result.getObject();
        task.setIsArchived(true);
        taskAccessHandler.saveAndFlush(task);
        return new ConditionalObject<>(task.createResponse());
    }
}
