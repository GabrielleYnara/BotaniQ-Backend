package definitions.user;

import definitions.SetupTestDefs;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.util.logging.Logger;

public class UserProfileDefs extends SetupTestDefs {
    private static Response response;
    private static String validToken ;
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
        validToken = response.jsonPath().getString("jwt");
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @When("I enter my first name, last name, and or bio")
    public void iEnterMyFirstNameLastNameAndOrBio() throws JSONException {
        log.info("WHEN - I enter my first name, last name, and or bio");
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        JSONObject requestBody = new JSONObject();
        requestBody.put("first name", "John");
        requestBody.put("last name", "Doe");
        requestBody.put("bio", "Love gardening and caring for my fresh herbs");
        request.headers(createAuthenticatedHeader(validToken));
        response = request.body(requestBody.toString()).put(BASE_URL + port + "/auth/users/profile/");
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Then("The personal information is updated and display success message")
    public void thePersonalInformationIsUpdatedAndDisplaySuccessMessage() {
        log.info("The personal information is updated and display success message");
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.headers(createAuthenticatedHeader(validToken));
        response = request.get(BASE_URL + port + "/auth/users/profile/");
        //check if info is updated.
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
