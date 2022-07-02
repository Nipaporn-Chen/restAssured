package workspaceRestAPI;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;

public class E2E_Project {

    public String path;
    String memberOf = "/workspaces/member-of";

    //What's a TestNG annotation that allows us to run Before each Test

    //Returning Token
    @BeforeTest
    public String setupLogInAndToken(){
        RestAssured.baseURI="https://api.octoperf.com";
        path = "/public/users/login";

        Map<String, Object> map = new HashMap<String,Object>();
        map.put("password", "HoneyHippo@2472#" );
        map.put("username", "amp6722.ja@gmail.com");

      return  given()
                .queryParams(map)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .post(path)//send request to end point
                .then()
                .statusCode(SC_OK) //verify status code = 200 or OK
                .extract()  //method that extracts to response JSON data
                .body() //Body extracted as JSON format
                .jsonPath()//Navigate using jsonPath
                .get("token"); // get value for key token

    }
    //Write a test for API endpoint member-of
    @Test
    public void verifyToken(){

    Response response = RestAssured.given()
                .header("Authorization",setupLogInAndToken())
                .when()
                .get(memberOf)
                .then()
                .log().all()
                .extract().response();

        //Verify status code
        Assert.assertEquals(SC_OK, response.statusCode());
        Assert.assertEquals("Default",response.jsonPath().getString("name[0]"));

        //TODO add tests for ID, userID, Description
    }
}
