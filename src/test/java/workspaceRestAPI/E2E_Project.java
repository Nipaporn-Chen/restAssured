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
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class E2E_Project {

    public String path;
    String memberOf = "/workspaces/member-of";
    Map<String, String> variables;
    String Id;
    String User_Id;
    Response response;
    String projectID;

    //What's a TestNG annotation that allows us to run Before each Test
    //BeforeMethods

    //Returning Token
    @BeforeTest
    public String setupLogInAndToken(){
        RestAssured.baseURI="https://api.octoperf.com";
        path = "/public/users/login";

        Map<String, Object> map = new HashMap<String,Object>();
        map.put("password", "xxxxxx" );
        map.put("username", "xxxxxx");

      return  given()
                .queryParams(map)
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .post(path)//send request to end point
                .then()
                .statusCode(SC_OK) //verify status code = 200 or OK by using interface class
                .extract()  //method that extracts response JSON data
                .body() //Body extracted as JSON format
                .jsonPath()//Navigate using jsonPath
                .get("token"); // get value for key token

    }
    //Write a test for API endpoint member-of
    @Test
    public void memberOf(){

    response = RestAssured.given()
                .header("Authorization",setupLogInAndToken()) //Sending authorization by using header
                .when()
                .get(memberOf)
                .then()
                .log().all() // Print all the log
                .extract().response(); //Extracting the response

        //Verify status code
        Assert.assertEquals(SC_OK, response.statusCode());

        //Verify the name
        Assert.assertEquals("Default",response.jsonPath().getString("name[0]"));

        //TODO add tests for ID, userID, Description
        Assert.assertEquals("OXlPv30BFcWANjCEt6zW", response.jsonPath().getString("id[0]"));
        Assert.assertEquals("km5Lv30BNDV4RwLKqKrt", response.jsonPath().getString("userId[0]"));
        Assert.assertEquals("", response.jsonPath().getString("description[0]"));

        //Save the id, so it can be used in other requests.
        Id = response.jsonPath().get("id[0]");

        //Save the userId, so it can be used in other requests.
        User_Id = response.jsonPath().get("userId[0]");

        //What can we use to Store data as Key and Value?
        variables = new HashMap<String, String>();
        variables.put("id", Id);
        variables.put("userID", User_Id);

//        Approach 1 to get Map key and value
//        variables.forEach((key, value) -> System.out.println(key + " : " + value));
//
//        Approach 2 to get Map key and value
//        for (Map.Entry<String,String> entry : variables.entrySet())
//            System.out.println("Key = " + entry.getKey() +
//                    ", Value = " + entry.getValue());

    }

    @Test(dependsOnMethods = {"memberOf"})
    public void createProject(){
        String requestBody = "{\"id\":\"\",\"created\":\"2021-03-11T06:15:20.845Z\",\"lastModified\":\"2021-03-11T06:15:20.845Z\",\"userId\":\"" + variables.get("userID") + "\",\"workspaceId\":\"" + variables.get("id") + "\",\"name\":\"testing22\",\"description\":\"testing\",\"type\":\"DESIGN\",\"tags\":[]}";

        response = RestAssured.given()
                .header("Content-type", "application/json")
                .header("Authorization", setupLogInAndToken())
                .and()
                .body(requestBody)
                .when()
                .post("/design/projects")
                .then()
                .log().all()
                .extract()
                .response();
        System.out.println(response.prettyPrint());

        //TODO : Create TestNG Assertion Name, id, userId, and workspaceID
        Assert.assertEquals("testing22", response.jsonPath().getString("name"));

        //TODO : Create TestNG Assertion id, userId, and workspaceID

        //Using hamcrest Matchers validation
        assertThat(response.jsonPath().getString("name"), is("testing22"));

        //Store projectID(id) in a variable for future use.
        projectID = response.jsonPath().get("id");
        System.out.println("New id created when creating a project: " + projectID);
    }

    @Test(dependsOnMethods = {"memberOf","createProject"})
    public void updateProject(){

        String requestBody1 = "{\"created\":1615443320845,\"description\":\"TLAUpate\",\"id\":\"" + projectID + "\",\"lastModified\":1629860121757,\"name\":\"tlaAccounting firm1\",\"tags\":[],\"type\":\"DESIGN\",\"userId\":\"" + variables.get("userID") + "\",\"workspaceId\":\"" + variables.get("id") + "\"}";

        response = RestAssured.given()
                .headers("Content-type", "application/json")
                .header("Authorization", setupLogInAndToken())
                .and()
                .body(requestBody1)
                .when()
                .put("/design/projects/"+projectID)
                .then()
                .log().all()
                .extract()
                .response();
        System.out.println("Project has been updated : " + projectID);

        //TODO : Homework add Assertions for id, name, type, userId, workspaceId, Status code, Content type
        assertThat(response.jsonPath().getString("type"), is("DESIGN"));
        assertThat(response.jsonPath().getString("userId"), is("km5Lv30BNDV4RwLKqKrt"));
        assertThat(response.jsonPath().getString("workspaceId"), is("OXlPv30BFcWANjCEt6zW"));
        assertThat(response.header("Content-Type"), is("application/json"));
        assertThat(response.statusCode(), is(200));


    }
    @Test(dependsOnMethods = {"memberOf", "createProject", "updateProject"})
    public void deleteProject(){
        response = RestAssured.given()
                .header("Authorization", setupLogInAndToken())
                .when()
                .delete("/design/projects/"+ projectID)
                .then()
                .log().all()
                .extract()
                .response();

        //TODO : Validate status code
        assertThat(response.statusCode(), is(204));
    }
}
