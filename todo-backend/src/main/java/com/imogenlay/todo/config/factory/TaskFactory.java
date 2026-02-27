package com.imogenlay.todo.config.factory;
 
import java.time.LocalDate;
import java.util.List;
 
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;
import com.imogenlay.todo.category.CategoryRepository;
import com.imogenlay.todo.category.entity.Category;
import com.imogenlay.todo.task.TaskRepository;
import com.imogenlay.todo.task.entity.Task;

@Component
@Profile({ "dev", "test" })
public class TaskFactory {
    private final CategoryRepository categoryRepository;
    private final TaskRepository taskRepository;

    public TaskFactory(TaskRepository taskRepository, CategoryRepository categoryRepository) {
        this.taskRepository = taskRepository;
        this.categoryRepository = categoryRepository;
    }

    public boolean repoEmpty() {
        return taskRepository.count() == 0;
    }
    
    public Task create(TaskFactoryOptions options) {
        Task task = new Task();
        Faker faker = new Faker();
        task.setName(options.name != null ? options.name : generateName(faker));
        task.setDueDate(options.dueDate != null ? options.dueDate : generateDate());
        task.setIsArchived(options.isArchived != null ? options.isArchived : Math.random() > 0.5);
        task.setCategory(options.category != null ? options.category : getRandomCategory()); 
        return task;
    }

    public Task create() {
        return create(new TaskFactoryOptions());
    }

    public Task createAndPersist(TaskFactoryOptions options) {
        Task task = create(options);
        return taskRepository.save(task);
    }

    public Task createAndPersist() {
        Task task = create();
        return taskRepository.save(task);
    }

    private String generateName(Faker faker) {
        double value = Math.random();
        if (value < 0.25)
            return "Eat " + faker.food().dish().toLowerCase();
        if (value < 0.5)
            return "Make art like " + faker.artist().name();
        if (value < 0.75)
            return "Go to " + faker.country().name();
        return "Befriend a " + faker.animal().name();
    }

    private LocalDate generateDate() {
        return LocalDate.ofYearDay(
            2025 + (int)(Math.random() * 3),
            (int)(Math.random() * 365));
    }

    private Category getRandomCategory() {
        List<Category> categories = categoryRepository.findAll();
        return categories.get((int) (Math.random() * categories.size()));
    }

    /*@Override
    public void clear() {
        this.categoryRepository.deleteAll(); 
    }*/
}

