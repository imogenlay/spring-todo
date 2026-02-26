package com.imogenlay.todo.config.factory;
 
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;
import com.imogenlay.todo.category.CategoryRepository;
import com.imogenlay.todo.category.entity.Category;

@Component
@Profile({ "dev", "test" })
public class CategoryFactory {
    private final CategoryRepository categoryRepository;

    public CategoryFactory(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category create(CategoryFactoryOptions options) {
        Category category = new Category();
        Faker faker = new Faker();
        category.setName((options.name != null ? options.name : faker.animal().name()).toLowerCase()); 
        category.setHue(options.hue != null ? options.hue : (int)(Math.random() * 360f)); 
        return category;
    }

    public boolean repoEmpty() {
        return categoryRepository.count() == 0;
    }
    
    public Category create() {
        return create(new CategoryFactoryOptions());
    }

    public Category createAndPersist(CategoryFactoryOptions options) {
        Category recording = create(options);
        return categoryRepository.save(recording);
    }

    public Category createAndPersist() {
        Category recording = create();
        return categoryRepository.save(recording);
    }

    /*@Override
    public void clear() {
        this.categoryRepository.deleteAll(); 
    }*/
}
