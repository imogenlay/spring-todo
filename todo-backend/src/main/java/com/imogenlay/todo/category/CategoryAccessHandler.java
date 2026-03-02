package com.imogenlay.todo.category;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.imogenlay.todo.category.entity.Category;
import com.imogenlay.todo.common.ConditionalObject;

@Component
public class CategoryAccessHandler {
    
    private final CategoryRepository categoryRepository;

    public CategoryAccessHandler(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    
    public List<Category> findAll(Sort sort) {
        return categoryRepository.findAll(sort);
    }

    public ConditionalObject<Category> findById(Long id) {
        Optional<Category> result = categoryRepository.findById(id);
        if (result.isEmpty())
            return new ConditionalObject<>(HttpStatus.NOT_FOUND, "Category with ID [" + id + "] does not exist.");

        return new ConditionalObject<>(result.get());
    }

    public Category findFirstCategoryThatIsNotId(Long id) {
        List<Category> categories = categoryRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        for (int i = 0; i < categories.size(); i++) 
            if (categories.get(i).getId().longValue() != id.longValue())
                return categories.get(i);
        
        Category category = new Category();
        category.setName("Unknown"); 
        saveAndFlush(category);
        return category;
    }

    public List<Category> findDistinctWithNamesIgnoreCase(List<String> names) {
        return categoryRepository.findDistinctWithNamesIgnoreCase(names);
    }

    public void saveAndFlush(Category category) { categoryRepository.saveAndFlush(category); }
    public void delete(Category category) { categoryRepository.delete(category); }
}
