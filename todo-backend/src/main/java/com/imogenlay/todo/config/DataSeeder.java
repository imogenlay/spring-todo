package com.imogenlay.todo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.imogenlay.todo.config.factory.CategoryFactory;
import com.imogenlay.todo.config.factory.CategoryFactoryOptions;

@Component()
@Profile("dev")
public class DataSeeder implements CommandLineRunner {
    
    private final CategoryFactory categoryFactory;

    public DataSeeder(CategoryFactory categoryFactory) {
        this.categoryFactory = categoryFactory;
    }

    @Override
    public void run(String... args) throws Exception {
        
        System.out.println("SEED NEW CATEGORIES");
        if (categoryFactory.repoEmpty())
        {
            categoryFactory.createAndPersist(
                new CategoryFactoryOptions()
                    .name("Unknown")
                    .hue(0));

            categoryFactory.createAndPersist(
                new CategoryFactoryOptions()
                    .name("School")
                    .hue(120));

            categoryFactory.createAndPersist(
                new CategoryFactoryOptions()
                    .name("Work")
                    .hue(30));

            categoryFactory.createAndPersist(
                new CategoryFactoryOptions()
                    .name("Food")
                    .hue(270));

            categoryFactory.createAndPersist(
                new CategoryFactoryOptions()
                    .name("Cleaning")
                    .hue(310));
        }
    }
}
