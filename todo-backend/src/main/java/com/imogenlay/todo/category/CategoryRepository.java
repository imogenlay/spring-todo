package com.imogenlay.todo.category;
 
import org.springframework.data.jpa.repository.JpaRepository;
import com.imogenlay.todo.category.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    
}
