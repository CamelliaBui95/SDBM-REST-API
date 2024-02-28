package fr.btn.sdbmrestapi.resources;

import io.restassured.internal.common.assertion.Assertion;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class IdentificationResourceTest {
    private static final String ENDPOINT = "/api/auth/login";

    @Test
    void login() {
        Response response = given()
                .contentType("application/json")
                .body(TestUtils.getUser())
                .when()
                .post(ENDPOINT)
                .then()
                .statusCode(200)
                .extract().response();
        String token = response.header("Authorization");

        Assertions.assertNotNull(token);
        Assertions.assertFalse(token.isEmpty());
    }

    @Test
    void register() {
    }
}