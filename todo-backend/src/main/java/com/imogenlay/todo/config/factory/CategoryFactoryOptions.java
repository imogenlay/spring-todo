package com.imogenlay.todo.config.factory;

public class CategoryFactoryOptions {
    
    public String name;
    public Integer hue;
    
    public CategoryFactoryOptions name(String name) {
        this.name = name;
        return this;
    }
    
    public CategoryFactoryOptions hue(Integer hue) {
        this.hue = hue;
        return this;
    }
}
