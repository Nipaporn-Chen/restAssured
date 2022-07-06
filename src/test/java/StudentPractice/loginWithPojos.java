package StudentPractice;

import Pojos.loginPojos;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

/**
 * POJOS stand for Plain Old Java Object
 * A pojo class must be public and its variables must be private
 * A pojo can have a constructor with arguments, the variables should have getters and setters to access data.
 * Pojo classes are used for creating JSON and XML Payloads - (DATA) for API
 */

public class loginWithPojos {

    @Test
    public void loginWithPojosData(){
        loginPojos data = new loginPojos();
        data.setPassword("HoneyHippo@2472#");
        data.setUserName("amp6722.ja@gmail.com");

        RestAssured.baseURI = "https://api.octoperf.com";
        String path = "/public/users/login";

        RestAssured.given()
                .queryParam("username", data.getUserName())
                .queryParam("password", data.getPassword())
                .when()
                .post(path)
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON)
                .and()
                .log().all();
    }
}
