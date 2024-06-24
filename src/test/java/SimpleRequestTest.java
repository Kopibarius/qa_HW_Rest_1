import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.is;

public class SimpleRequestTest {

    @Test
    void getSingleUser() {
        given()
                .when()
                .log().uri()
                .get("https://reqres.in/api/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.id",is(2));
    }

    @Test
    void getUnknownUser() {
        given()
                .when()
                .log().uri()
                .get("https://reqres.in/api/unknown/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("SilngleUserSchema.json"));
    }

    @Test
    void postCreatingUser() {

        String body = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";

        given()
                .body(body)
                .when()
                .log().uri()
                .post("https://reqres.in/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201);
    }

    @Test
    void postNotCreatingUser() {

        given()
                .when()
                .log().uri()
                .post("https://reqres.in/api/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(415);
    }

    @Test
    void patchUser() {

        String body = "{ \"name\": \"morpheus1\", \"job\": \"leader1\" }";

        given()
                .body(body)
                .when()
                .log().uri()
                .patch("https://reqres.in/api/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200);
    }
}
