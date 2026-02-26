package com.imogenlay.todo.category;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.imogenlay.todo.category.dtos.CategoryResponse;
import com.imogenlay.todo.category.dtos.CreateCategoryDto;
import com.imogenlay.todo.category.entity.Category;
import com.imogenlay.todo.common.error.ConditionalObject; 

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
    
    public ConditionalObject<CategoryResponse> create(CreateCategoryDto data) {
        String name = normaliseName(data.name());
        List<Category> categories = this.categoryRepository.findDistinctWithNamesIgnoreCase(List.of(name));
        if (!categories.isEmpty())
            return new ConditionalObject<>(HttpStatus.BAD_REQUEST, "Category already exists.");

        Category category = new Category();
        category.setName(name);
        category.setHue(data.hue());
        categoryRepository.saveAndFlush(category);
        return new ConditionalObject<>(category.createResponse());
    }
    
    public ConditionalObject<CategoryResponse> update(Long id, CreateCategoryDto data) { 
        Optional<Category> optional = this.categoryRepository.findById(id);
        if (optional.isEmpty())
            return new ConditionalObject<>(HttpStatus.NOT_FOUND, "Category with ID [" + id + "] does not exist.");

        Category category = optional.get();
        category.setName(normaliseName(data.name()));
        category.setHue(data.hue());
        categoryRepository.save(category);
 
        return new ConditionalObject<>(category.createResponse());
    }

    private String normaliseName(String name) {
        return name.toLowerCase();
    }
}
