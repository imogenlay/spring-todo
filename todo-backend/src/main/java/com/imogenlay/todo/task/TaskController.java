package com.imogenlay.todo.task;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
 
import com.imogenlay.todo.common.SortOrder;
import com.imogenlay.todo.common.error.ConditionalObject;
import com.imogenlay.todo.task.dtos.CreateTaskDto;
import com.imogenlay.todo.task.dtos.TaskQueryParams;
import com.imogenlay.todo.task.dtos.TaskResponse;
import com.imogenlay.todo.task.dtos.UpdateTaskDto;

import jakarta.validation.Valid; 

@RestController()
@RequestMapping("/tasks")
public class TaskController {
    
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> findAll(
        @RequestParam(required = false) List<String> categories, 
        @RequestParam(defaultValue = "DESC") SortOrder order)
    {
        TaskQueryParams params = new TaskQueryParams(categories, order);

        Sort sort = Sort.by(
            params.orderOrDefault() == SortOrder.ASC ? Sort.Direction.ASC : Sort.Direction.DESC,
            "name"
        );

        return ResponseEntity.status(HttpStatus.OK).body(taskService.findAll(params.categories(), sort));
    }
  
    @PostMapping()
    public ResponseEntity<TaskResponse> create(@Valid @RequestBody CreateTaskDto data) {
        ConditionalObject<TaskResponse> result = taskService.create(data);
        if (result.hasError())
            result.throwError();

        return ResponseEntity.status(HttpStatus.CREATED).body(result.getObject()); 
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> update(
        @PathVariable Long id,
        @Valid @RequestBody UpdateTaskDto data)
    { 
        ConditionalObject<TaskResponse> result = taskService.update(id, data);
        if (result.hasError())
            result.throwError(); 

        return ResponseEntity.status(HttpStatus.OK).body(result.getObject());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    { 
        ConditionalObject<TaskResponse> result = taskService.delete(id);
        if (result.hasError())
            result.throwError();
        
        return ResponseEntity.noContent().build();
    }
}
