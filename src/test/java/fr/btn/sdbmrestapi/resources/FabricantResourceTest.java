package fr.btn.sdbmrestapi.resources;

import fr.btn.sdbmrestapi.metier.Fabricant;
import io.restassured.internal.common.assertion.Assertion;
import io.restassured.internal.common.assertion.AssertionSupport;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FabricantResourceTest {
    private final String ENDPOINT = "/sdbm/api/fabricants";
    private Fabricant testFabricant = new Fabricant(0, "TEST FABRICANT");
    @Test
    @Order(2)
    void getAll() {
        given()
                .when()
                .get(ENDPOINT)
                .then()
                .contentType("application/json")
                .statusCode(200)
                .log()
                .all();
    }

    @Test
    @Order(3)
    void getById() {
        Fabricant result = given()
                .contentType("application/json")
                .when()
                .get(ENDPOINT + "/" + testFabricant.getId())
                .then()
                .contentType("application/json")
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(Fabricant.class);

        Assertions.assertEquals(testFabricant.getId(), result.getId());
        Assertions.assertEquals(testFabricant.getName(), result.getName());
    }

    @Test
    @Order(1)
    void post() {
        Fabricant result = given()
                .contentType("application/json")
                .body(testFabricant)
                .when()
                .post(ENDPOINT)
                .then()
                .contentType("application/json")
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .as(Fabricant.class);


        if(result != null)
            testFabricant = result;

        Assertions.assertEquals("TEST FABRICANT", result.getName());
    }

    @Test
    @Order(4)
    void put() {

        testFabricant.setName("UPDATED");
        Fabricant result = given()
                .contentType("application/json")
                .body(testFabricant)
                .when()
                .put(ENDPOINT + "/" + testFabricant.getId())
                .then()
                .contentType("application/json")
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .as(Fabricant.class);

        Assertions.assertEquals(testFabricant.getId(), result.getId());
        Assertions.assertEquals(testFabricant.getName(), result.getName());
    }

    @Test
    @Order(5)
    void delete() {
        given()
                .contentType("application/json")
                .when()
                .delete(ENDPOINT + "/" + testFabricant.getId())
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);

        given()
                .contentType("application/json")
                .when()
                .get(ENDPOINT + "/" + testFabricant.getId())
                .then()
                .statusCode(HttpStatus.SC_NOT_FOUND);
    }
}