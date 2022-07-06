package StudentPractice;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class apiReadFromJsonBody {

    @Test
    public void readFromJson() throws FileNotFoundException {

        //TODO: how can you read from a file using Java
        File requestBody = new File("src\\resources\\requestBody.json");

        Response response = RestAssured.given()
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .post("https://fakerestapi.azurewebsites.net/api/v1/Activities")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();

        assertThat(response.statusCode(), is(200));
    }

    //TODO Task 1: Create a json file Authors ==> Send a Post request
    // Validate Status code, content-type and all the fields from the request body
    @Test
    public void readFromFileAuthors(){

        File requestBody1 = new File("src\\resources\\requestBodyAuthors.json");

        Response response1 = RestAssured.given()
                .header("Content-type", "application/json")
                .and()
                .body(requestBody1)
                .when()
                .post("https://fakerestapi.azurewebsites.net/api/v1/Authors")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();

        assertThat(response1.statusCode(), is(200));
        assertThat(response1.jsonPath().getString("id"), is("2"));
        assertThat(response1.jsonPath().getString("idBook"), is("2"));
        assertThat(response1.jsonPath().getString("firstName"), is("Test"));
        assertThat(response1.jsonPath().getString("lastName"), is("Books"));
        assertThat(response1.header("Content-Type"), is("application/json; charset=utf-8; v=1.0"));

        }

    //TODO Task 2: Create a json file Books -> Send a Post request
    @Test
    public void readFromFileBooks() {

        File requestBody2 = new File("src\\resources\\requestBodyBooks.json");

        Response response2 = RestAssured.given()
                .header("Content-type", "application/json")
                .and()
                .body(requestBody2)
                .when()
                .post("https://fakerestapi.azurewebsites.net/api/v1/Books")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();

        assertThat(response2.statusCode(), is(200));
        assertThat(response2.jsonPath().getString("id"), is("1"));
        assertThat(response2.jsonPath().getString("title"), is("Learning API"));
        assertThat(response2.jsonPath().getString("description"), is("For Beginner"));
        assertThat(response2.jsonPath().getString("pageCount"), is("200"));
        assertThat(response2.header("Content-Type"), is("application/json; charset=utf-8; v=1.0"));
    }
}
