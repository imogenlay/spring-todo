package com.imogenlay.todo.task;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.imogenlay.todo.task.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> { 
        
    @Query("""
        SELECT t
        FROM Task t
        WHERE LOWER(t.category.name) IN :categories
    """)
    List<Task> findByCategoryNameIgnoreCase(
        @Param("categories") List<String> categories,
        Sort sort
    );
}
