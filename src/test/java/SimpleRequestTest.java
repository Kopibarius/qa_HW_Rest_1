import helper.CustomAllureListener;
import models.UserFromResponse;
import models.UsersRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.is;
import static helper.CustomAllureListener.withCustomTemplates;


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
        UserFromResponse dataUserFromResponse = step("Отправка запроса", () ->
                given()
                .filter(CustomAllureListener.withCustomTemplates())
                .spec(SpecForSimpleTest.request)
                .when()
                .get("/unknown/2")
                .then()
                .body(matchesJsonSchemaInClasspath("SilngleUserSchema.json"))
                .statusCode(200)
                .log().status()
                .log().body()
                .extract().as(UserFromResponse.class));

        step("Проверка ответа", () -> {
            Assertions.assertEquals("https://reqres.in/#support-heading", dataUserFromResponse.getUserSupport().getUrl().toString());
                }
        );
    }

    @Test
    void postCreatingUser() {

        UsersRequest body = new UsersRequest();
        body.setName("morpheus");
        body.setJob("leader");

        step("Отправка запроса", () -> {
            given()
                    .filter(CustomAllureListener.withCustomTemplates())
                    .body(body)
                    .spec(SpecForSimpleTest.request)
                    .when()
                    .post("/users")
                    .then()
                    .log().status()
                    .log().body()
                    .statusCode(201);
        });
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

        UsersRequest body = new UsersRequest();
        body.setName("morpheus");
        body.setJob("leader");

        step("Отправка запроса", () -> {
        given()
                .filter(CustomAllureListener.withCustomTemplates())
                .body(body)
                .spec(SpecForSimpleTest.request)
                .when()
                .patch("/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200);
        });
    }
}
