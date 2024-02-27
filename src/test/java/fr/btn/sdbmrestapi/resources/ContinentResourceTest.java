package fr.btn.sdbmrestapi.resources;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;

class ContinentResourceTest {

    @Test
    void getAll() {
        given()
                .when()
                .get("/api/continents")
                .then()
                .contentType("application/json")
                .statusCode(200)
                .body("size()", is(5));
    }
}