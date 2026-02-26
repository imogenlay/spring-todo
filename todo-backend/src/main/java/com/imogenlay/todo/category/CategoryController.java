package com.imogenlay.todo.category;

import java.util.List;

import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Sort;
import org.springframework.boot.context.properties.bind.validation.ValidationErrors;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.imogenlay.todo.category.dtos.CategoryQueryParams;
import com.imogenlay.todo.category.dtos.CategoryResponse;
import com.imogenlay.todo.category.dtos.CreateCategoryDto;
import com.imogenlay.todo.common.Either;
import com.imogenlay.todo.common.SortOrder;
import com.imogenlay.todo.common.error.ConditionalObject;

import jakarta.validation.Valid;

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

        return ResponseEntity.ok(categoryService.findAll(sort));
    }
  
    @PostMapping()
    public ResponseEntity<CategoryResponse> create(@Valid @RequestBody CreateCategoryDto data) {
        ConditionalObject<CategoryResponse> result = this.categoryService.create(data);
        if (result.hasError())
            result.throwError(); 

        return ResponseEntity.status(201).body(result.getObject());
    }
}