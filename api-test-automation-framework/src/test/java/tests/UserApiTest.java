package tests;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UserApiTest {

    @Test
    public void getUserTest() {

        given()
                .baseUri("https://jsonplaceholder.typicode.com")
                .when()
                .get("/users/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1));
    }

    @Test
    public void test1() {
        System.out.println("TEST RUNNING");
    }
}
