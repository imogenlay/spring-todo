package com.imogenlay.todo.task.entity;
 
import java.time.LocalDate; 

import com.imogenlay.todo.category.entity.Category;
import com.imogenlay.todo.common.entity.BaseTimestampedEntity;
import com.imogenlay.todo.common.entity.TimestampedEntityListener;
import com.imogenlay.todo.task.dtos.TaskResponse;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tasks")
@EntityListeners(TimestampedEntityListener.class)
public class Task extends BaseTimestampedEntity {
    
    private String name; 
    private LocalDate dueDate;
    private Boolean isArchived;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Task() {}
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
 
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public Boolean getIsArchived() { return isArchived; }
    public void setIsArchived(Boolean isArchived) { this.isArchived = isArchived; }
    
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public TaskResponse createResponse() {
        return new TaskResponse(getId(), getName(), getDueDate(), getIsArchived(), 
            getCategory().getName(), getCategory().getId());
    }
}
