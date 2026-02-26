package com.imogenlay.todo.category.entity;
 
import java.time.LocalDateTime;

import com.imogenlay.todo.category.dtos.CategoryResponse;
import com.imogenlay.todo.common.entity.BaseEntity;
import com.imogenlay.todo.common.entity.TimestampedEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;

@Entity
@Table(name = "categories")
@EntityListeners(TimestampedEntityListener.class)
public class Category extends BaseEntity {
    
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Category() {}
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public CategoryResponse createResponse() {
        return new CategoryResponse(getId(), getName());
    }
}
