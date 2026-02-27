package com.imogenlay.todo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.imogenlay.todo.config.factory.CategoryFactory;
import com.imogenlay.todo.config.factory.CategoryFactoryOptions;
import com.imogenlay.todo.config.factory.TaskFactory; 

@Component()
@Profile("dev")
public class DataSeeder implements CommandLineRunner {
    
    private final CategoryFactory categoryFactory;
    private final TaskFactory taskFactory;

    public DataSeeder(CategoryFactory categoryFactory, TaskFactory taskFactory) {
        this.categoryFactory = categoryFactory;
        this.taskFactory = taskFactory;
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

        System.out.println("SEED NEW TASKS");
        if (taskFactory.repoEmpty()) {
            for (int i = 0; i < 80; i++) 
                taskFactory.createAndPersist();
        }
    }
}
