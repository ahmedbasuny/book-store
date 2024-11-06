package com.book.store.system.catalog.web.controllers;

import com.book.store.system.catalog.AbstractIntegrationTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@Sql("/test-data.sql")
class ProductControllerTest extends AbstractIntegrationTest {

    @Test
    void shouldReturnProducts() {
        given().contentType(ContentType.JSON)
                .when()
                .get("/api/v1/products")
                .then()
                .statusCode(200)
                .body("content", hasSize(10))
                .body("totalElements", is(14))
                .body("totalPages", is(2))
                .body("first", is(true))
                .body("last", is(false))
                .body("number", is(0))
                .body("pageable.pageNumber", is(0));
    }
}