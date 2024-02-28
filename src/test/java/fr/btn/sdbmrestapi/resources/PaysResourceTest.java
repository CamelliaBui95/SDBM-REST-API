package fr.btn.sdbmrestapi.resources;

import fr.btn.sdbmrestapi.metier.Continent;
import fr.btn.sdbmrestapi.metier.Pays;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(
        MethodOrderer.OrderAnnotation.class
)
class PaysResourceTest {
    private static final String ENDPOINT = "/api/pays";
    private static Pays testPays = new Pays(0, "TEST PAYS", new Continent(1, ""));
    private static String token;

    @Test
    @Order(1)
    void getPaysList() {
        given()
                .when()
                .get(ENDPOINT)
                .then()
                .contentType("application/json")
                .statusCode(200);
    }

    @Test
    @Order(3)
    void getPaysListWithQueries() {
        String endpoint = String.format(ENDPOINT +  "?name=%s&continentId=%s", testPays.getName(), testPays.getContinent().getId());
        given()
                .when()
                .get(endpoint)
                .then()
                .contentType("application/json")
                .statusCode(200)
                .body("size()", is(1))
                .log()
                .all();

    }

    @Test
    @Order(2)
    void postPays() {
        String newToken = given()
                        .contentType("application/json")
                        .body(TestUtils.getUser())
                        .when()
                        .post("/api/auth/login")
                        .then()
                        .statusCode(200)
                        .extract().response().header("Authorization");

        token = newToken;

        Pays result = given()
                .contentType("application/json")
                .header("Authorization", token)
                .body(testPays)
                .when()
                .post(ENDPOINT)
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .contentType("application/json")
                .extract()
                .response()
                .body().as(Pays.class);

        if(result != null)
            testPays.setId(result.getId());

        assertThat(result.getName(), equalTo("TEST PAYS"));
    }

    @Test
    @Order(4)
    void updatePays() {
        testPays.setName("UPDATED");

        Pays result = given()
                .contentType("application/json")
                .header("Authorization", token)
                .body(testPays)
                .when()
                .put(ENDPOINT + "/" + testPays.getId())
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract().as(Pays.class);

        assertThat(result.getName(), equalTo("UPDATED"));
    }

    @Test
    @Order(5)
    void deleteById() {
        given()
                .when()
                .header("Authorization", token)
                .delete(ENDPOINT + "/" + testPays.getId())
                .then()
                .statusCode(HttpStatus.SC_NO_CONTENT);
    }
}