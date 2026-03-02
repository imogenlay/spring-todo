package com.imogenlay.todo.category;
 
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.imogenlay.todo.category.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    @Query("""
        SELECT DISTINCT c
        FROM Category c
        WHERE LOWER(c.name) IN :names
    """)
    List<Category> findDistinctWithNamesIgnoreCase(
        @Param("names") List<String> names);

        
    @Query("""
        SELECT COALESCE(MIN(c.id), 0)
        FROM Category c
    """)
    Long getMinId();

    @Query("""
        SELECT COALESCE(MAX(c.id), 0)
        FROM Category c
    """)
    Long getMaxId();
}
