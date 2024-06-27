import models.UserFromRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.is;

public class SimpleRequestTest {

    @Test
    void getSingleUser() {
        given()
                .spec(SpecForSimpleTest.request)
                .when()
                .get("/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.id", is(2));
    }

    @Test
    void getUnknownUser() {
        UserFromRequest dataUserFromRequest = given()
                .spec(SpecForSimpleTest.request)
                .when()
                .get("/unknown/2")
                .then()
                .body(matchesJsonSchemaInClasspath("SilngleUserSchema.json"))
                .statusCode(200)
                .log().status()
                .log().body()
                .extract().as(UserFromRequest.class);

        Assertions.assertEquals("https://reqres.in/#support-heading", dataUserFromRequest.getUserSupport().getUrl().toString());
    }

    @Test
    void postCreatingUser() {

        String body = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";

        given()
                .body(body)
                .spec(SpecForSimpleTest.request)
                .when()
                .post("/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201);
    }

    @Test
    void postNotCreatingUser() {

        given()
                .spec(SpecForSimpleTest.request)
                .when()
                .post("/use1rs")
                .then()
                .log().status()
                .log().body()
                .statusCode(201);
    }

    @Test
    void patchUser() {

        String body = "{ \"name\": \"morpheus1\", \"job\": \"leader1\" }";

        given()
                .body(body)
                .spec(SpecForSimpleTest.request)
                .when()
                .patch("/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200);
    }
}
