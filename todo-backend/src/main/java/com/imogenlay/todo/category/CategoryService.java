package com.imogenlay.todo.category;

import java.util.List; 
import org.springframework.data.domain.Sort;

import org.springframework.stereotype.Service;

import com.imogenlay.todo.category.dtos.CategoryResponse; 

@Service
public class CategoryService {
    
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository)
    {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryResponse> findAll(Sort sort) {
        return categoryRepository.findAll(sort)
            .stream().map((c) -> c.createResponse()).toList();
    }
}
