package com.imogenlay.todo.category;

import java.util.List; 

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imogenlay.todo.category.dtos.CategoryQueryParams;
import com.imogenlay.todo.category.dtos.CategoryResponse;
import com.imogenlay.todo.category.dtos.CreateCategoryDto;
import com.imogenlay.todo.category.dtos.UpdateCategoryDto;
import com.imogenlay.todo.category.entity.Category;
import com.imogenlay.todo.common.SortOrder;
import com.imogenlay.todo.common.error.ConditionalObject;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController()
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> findAll(
        @RequestParam(defaultValue = "DESC") SortOrder order)
    {
        CategoryQueryParams params = new CategoryQueryParams(order);

        Sort sort = Sort.by(
            params.orderOrDefault() == SortOrder.ASC ? Sort.Direction.ASC : Sort.Direction.DESC,
            "name"
        );

        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findAll(sort));
    }
  
    @PostMapping()
    public ResponseEntity<CategoryResponse> create(@Valid @RequestBody CreateCategoryDto data) {
        ConditionalObject<CategoryResponse> result = this.categoryService.create(data);
        if (result.hasError())
            result.throwError(); 

        return ResponseEntity.status(HttpStatus.CREATED).body(result.getObject()); 
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> update(
        @PathVariable Long id,
        @Valid @RequestBody UpdateCategoryDto data)
    { 
        ConditionalObject<CategoryResponse> result = this.categoryService.update(id, data);
        if (result.hasError())
            result.throwError(); 

        return ResponseEntity.status(HttpStatus.OK).body(result.getObject());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    { 
        ConditionalObject<Category> result = this.categoryService.delete(id);
        if (result.hasError())
            result.throwError();
        
        return ResponseEntity.noContent().build();
    }
}