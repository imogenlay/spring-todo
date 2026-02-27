package com.imogenlay.todo.category;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.imogenlay.todo.category.dtos.CategoryResponse;
import com.imogenlay.todo.category.dtos.CreateCategoryDto;
import com.imogenlay.todo.category.dtos.UpdateCategoryDto;
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
    
    public ConditionalObject<Category> findById(Long id) {
        Optional<Category> result = categoryRepository.findById(id);
        if (result.isEmpty())
            return new ConditionalObject<>(HttpStatus.NOT_FOUND, "Category with ID [" + id + "] does not exist.");

        return new ConditionalObject<>(result.get());
    }

    public ConditionalObject<CategoryResponse> create(CreateCategoryDto data) {
        String name = normaliseName(data.name());
        List<Category> categories = categoryRepository.findDistinctWithNamesIgnoreCase(List.of(name));
        if (!categories.isEmpty())
            return new ConditionalObject<>(HttpStatus.BAD_REQUEST, "Category '" + name + "' already exists.");

        Category category = new Category();
        category.setName(name);
        category.setHue(data.hue());
        categoryRepository.saveAndFlush(category);
        return new ConditionalObject<>(category.createResponse());
    }
    
    public ConditionalObject<CategoryResponse> update(Long id, UpdateCategoryDto data) { 
        ConditionalObject<Category> result = findById(id);
        if (result.hasError())
            return new ConditionalObject<>(result);

        Category category = result.getObject();
        if (data.name() != null)
            category.setName(normaliseName(data.name()));
        if (data.hue() != null)
            category.setHue(data.hue());
        categoryRepository.save(category);
 
        return new ConditionalObject<>(category.createResponse());
    }

    public ConditionalObject<Void> delete(Long id) {
        ConditionalObject<Category> result = findById(id);
        if (result.hasError())
            return new ConditionalObject<>(result);
        categoryRepository.delete(result.getObject()); 

        return new ConditionalObject<>(null);
    }

    private String normaliseName(String name) {
        return name.toLowerCase();
    }
}
