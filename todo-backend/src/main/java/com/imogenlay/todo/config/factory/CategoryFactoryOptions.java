package com.imogenlay.todo.config.factory;

public class CategoryFactoryOptions {
    
    public String name;
    public Short hue;
    
    public CategoryFactoryOptions name(String name) {
        this.name = name;
        return this;
    }
    
    public CategoryFactoryOptions name(Short hue) {
        this.hue = hue;
        return this;
    }
}
