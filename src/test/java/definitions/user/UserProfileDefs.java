package definitions.user;

import definitions.SetupTestDefs;
import io.cucumber.java.en.Given;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.http.HttpStatus;

import java.util.logging.Logger;

public class UserProfileDefs extends SetupTestDefs {
    private static Response response;
    private static final Logger log = Logger.getLogger(UserLoginDefs.class.getName());
    @Given("I am logged in")
    public void iAmLoggedIn() throws JSONException {
        log.info("GIVEN - I am logged in");
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        JSONObject requestBody = new JSONObject();
        requestBody.put("email", "newuser@email.com");
        requestBody.put("password", "password");
        request.header("Content-Type", "application/json");
        response = request.body(requestBody.toString()).post(BASE_URL + port + "/auth/users/login/");
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
