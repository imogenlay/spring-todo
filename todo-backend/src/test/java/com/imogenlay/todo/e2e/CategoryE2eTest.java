package com.imogenlay.todo.e2e;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Profile;

import com.imogenlay.todo.E2eBase;
 
//import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import java.util.HashMap;
import java.util.Map;

@Profile("test")
class CategoryE2eTest extends E2eBase {
    
    private Map<String, Object> createValidDTO() {
        Map<String, Object> dto = new HashMap<>();
        dto.put("name", "Work");
        dto.put("hue", 120);
        return dto;
    }
    
    @Test
    void getCategories_returnsArrayWithCode200() {

        categoryFactory.createAndPersist();

        test()
        .when()
            .get("/categories")
        .then() 
            .statusCode(200)
            .body(matchesJsonSchemaInClasspath("schemas/category-response-schema.json"));
    } 

    @Test
    void postCategory_validDto_returns201() {
        var dto = createValidDTO();

        test()
            .contentType("application/json")
            .body(dto)
        .when()
            .post("/categories")
        .then()
            .statusCode(201);
    }

    @Test
    void postCategory_invalidDto_returns400() {
        Map<String, Object> dto = new HashMap<>();
        dto.put("name", "School");
        // Does not contain hue!

        test()
            .contentType("application/json")
            .body(dto)
        .when()
            .post("/categories")
        .then()
            .statusCode(400);
    }
}