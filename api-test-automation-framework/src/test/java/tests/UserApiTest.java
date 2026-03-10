package tests;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UserApiTest {

//    @Test
//    public void getUserTest() {
//
//        given()
//                .baseUri("https://reqres.in")
//                .when()
//                .get("/api/users/2")
//                .then()
//                .statusCode(200)
//                .body("data.id", equalTo(2));
//    }

    @Test
    public void test1() {
        System.out.println("TEST RUNNING");
    }
}
