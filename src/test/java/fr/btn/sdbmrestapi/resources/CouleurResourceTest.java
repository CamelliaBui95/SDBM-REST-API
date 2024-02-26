package fr.btn.sdbmrestapi.resources;

import fr.btn.sdbmrestapi.metier.Couleur;
import jakarta.ws.rs.core.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;


import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CouleurResourceTest {
    private final String ENDPOINT = "/sdbm/api/couleurs";
    private Couleur testCouleur = new Couleur(0, "TEST COULEUR");
    @Test
    @Order(1)
    void getAll() {
        given()
                .when()
                .get(ENDPOINT)
                .then()
                .contentType("application/json")
                .statusCode(200);
                //.body("size()", is(5));
    }

    @Test
    @Order(2)
    void getById() {
        Couleur couleur =
                given()
                        .when()
                        .get(ENDPOINT + "/1")
                        .then()
                        .contentType("application/json")
                        .statusCode(200)
                        .extract()
                        .response()
                        .getBody().as(Couleur.class);

        assertThat(couleur.getId(), equalTo(1));
    }

    @Test
    @Order(3)
    void insert() {
        Couleur res = given()
                            .contentType("application/json")
                            .body(testCouleur)
                            .when()
                            .post(ENDPOINT)
                            .then()
                            .statusCode(HttpStatus.SC_CREATED)
                            .contentType("application/json")
                            .extract().response().body().as(Couleur.class);

        if(res != null && res.getId() != 0)
            testCouleur.setId(res.getId());

        assertThat(res.getName(), equalTo("TEST COULEUR"));
    }

    @Test
    @Order(4)
    void update() {
        int testCouleurId = testCouleur.getId();
        Couleur updatedCouleur = given()
                .contentType("application/json")
                .body(new Couleur(testCouleurId, "UPDATED TEST COULEUR"))
                .when()
                .put(ENDPOINT + "/" + testCouleurId)
                .then()
                .statusCode(200)
                .contentType("application/json")
                .extract().as(Couleur.class);

        if(updatedCouleur != null && updatedCouleur.getId() != 0)
            testCouleur.setName(updatedCouleur.getName());

        assertThat(updatedCouleur.getName(), equalTo("UPDATED TEST COULEUR"));
    }

    @Test
    @Order(5)
    void deleteById() {
        given()
                .when()
                .delete(ENDPOINT + "/" + testCouleur.getId())
                .then()
                .statusCode(204);

    }
}