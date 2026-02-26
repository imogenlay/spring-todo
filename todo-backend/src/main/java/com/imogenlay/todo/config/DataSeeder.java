package com.imogenlay.todo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component()
@Profile("dev")
public class DataSeeder implements CommandLineRunner {
  
    @Override
    public void run(String... args) throws Exception {
        
    }
    
}
