package com.imogenlay.todo.task;
 
import java.util.List;
import java.util.Optional; 

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.imogenlay.todo.category.entity.Category; 
import com.imogenlay.todo.task.dtos.TaskResponse; 
import com.imogenlay.todo.task.entity.Task; 

@Component
public class TaskAccessHandler {
    
    private final TaskRepository taskRepository;

    public TaskAccessHandler(TaskRepository taskRepository)
    {
        this.taskRepository = taskRepository;
    }

    public List<Task> findAll(Sort sort) {
        return taskRepository.findAll(sort);
    }

    public List<TaskResponse> findAll(List<String> categories, Sort sort) {
        List<Task> tasks;
        if (categories == null || categories.isEmpty())
            tasks = findAll(sort);
        else {
            tasks = findByCategoryNameIgnoreCase(
                categories.stream().map((c) -> c.toLowerCase()).toList(), sort);
        }
        
        return tasks.stream().map((t) -> t.createResponse()).toList();
    }    

    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    public List<Task> findByCategoryNameIgnoreCase(List<String> categories, Sort sort) {
        return taskRepository.findByCategoryNameIgnoreCase(categories, sort);
    }
    
    public void saveAndFlush(Task task) { taskRepository.saveAndFlush(task); }
    public void delete(Task task) { taskRepository.delete(task); }

    public void setCategoryOnId(Long id, Category category) { 
        Optional<Task> result = findById(id);
        if (result.isPresent())
        {
            Task task = result.get();
            task.setCategory(category);
        }
    }
}
