package API_Testing.API_Day2;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import javax.security.auth.login.Configuration;
import java.util.HashMap;
import java.util.Map;

public class _02_LogInTypes {
    /*
    Log in with Full URL with query params and verify status Content-type is equal to JSON
     */
    @Test
    public void testUsingQueryParams(){
        RestAssured.given()
                .when()
                .post("https://api.octoperf.com/public/users/login?password=HoneyHippo@2472#&username=amp6722.ja@gmail.com")
                .then()
                .assertThat().statusCode(200)
                .and()
                .assertThat().contentType(ContentType.JSON);

    }
    /*
    Log in using Map to verify Content Type
    Map ==> Store key and value -->Hashmap implements Map, We can store different data type of Object
     */

       @Test
    public void LoginWithMap(){
        RestAssured.baseURI="https://api.octoperf.com";
        String path = "/public/users/login";

        //Write a MAP
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("username","amp6722.ja@gmail.com");
        map.put("password", "HoneyHippo@2472#");

        RestAssured.given()
                .queryParams(map)
                .when()
                .post(path)
                .then()
                .assertThat()
                .contentType(ContentType.JSON)
                .and()
                .assertThat()
                .statusCode(200);
    }

    //Using query Param
    @Test
    public void LoginWithQueryParam(){
        RestAssured.baseURI="https://api.octoperf.com";
        String path = "/public/users/login";

        RestAssured.given()
                .queryParams("username", "amp6722.ja@gmail.com")
                .queryParams("password", "HoneyHippo@2472#")
                .when()
                .post(path)
                .then()
                .assertThat()
                .contentType(ContentType.JSON)
                .and()
                .assertThat()
                .statusCode(200);
    }
    //Using ConfigurationReader.getproperty("username", "password")
}
