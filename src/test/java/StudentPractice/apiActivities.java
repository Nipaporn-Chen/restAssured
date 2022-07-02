package StudentPractice;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

public class apiActivities {

    // Testing URI = https://fakerestapi.azurewebsites.net/api/v1/

    // TODO 1: Using Rest Assured validate the status code for endpoint /Activities
    @Test
    public void verifyStatusCode(){
        RestAssured.given()
                .when()
                .get("https://fakerestapi.azurewebsites.net/api/v1/Activities")
                .then()
                .statusCode(200);
        System.out.println("Test verified, status code is 200 ok");//Optional
    }

    // TODO 2: Using Rest Assured Validate Content-Type  is application/json; charset=utf-8; v=1.0
    // for endpoint /Activities
    @Test
    public void verifyContentType(){
        RestAssured.given()
                .when()
                .get("https://fakerestapi.azurewebsites.net/api/v1/Activities")
                .then()
                .assertThat()
                .contentType(ContentType.JSON);
        System.out.println("Test verified, content type = JSON");//Optional
    }
    @Test
    public void verifyContentTypeOption2(){
        RestAssured.given()
                .when()
                .get("https://fakerestapi.azurewebsites.net/api/v1/Activities")
                .then()
                .assertThat().header("Content-Type", "application/json; charset=utf-8; v=1.0");
    }
    // TODO 1: Using Rest Assured validate the status code for endpoint /Activities/12

    // TODO 2: Using Rest Assured Validate Content-Type  is application/json; charset=utf-8; v=1.0
    // for endpoint /Activities/12


}
