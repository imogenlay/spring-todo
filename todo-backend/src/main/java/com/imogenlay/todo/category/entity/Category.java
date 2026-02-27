package com.imogenlay.todo.category.entity;
 
import com.imogenlay.todo.category.dtos.CategoryResponse;
import com.imogenlay.todo.common.entity.BaseTimestampedEntity;
import com.imogenlay.todo.common.entity.TimestampedEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Table;

@Entity
@Table(name = "categories")
@EntityListeners(TimestampedEntityListener.class)
public class Category extends BaseTimestampedEntity {
    
    private String name;
    private Integer hue;

    public Category() {}
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getHue() { return hue; }
    public void setHue(Integer hue) { this.hue = hue; }

    public CategoryResponse createResponse() {
        return new CategoryResponse(getId(), getName(), getHue());
    }
}
