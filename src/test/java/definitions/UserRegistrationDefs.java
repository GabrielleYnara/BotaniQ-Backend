package definitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.logging.Logger;


public class UserRegistrationDefs extends SetupTestDefs{
    private static Response response;
    private static final Logger log = Logger.getLogger(UserRegistrationDefs.class.getName());

    @When("I enter a unique email and password")
    public void iEnterAUniqueEmailAndPassword() throws JSONException {
        log.info("User Registration - I enter a unique email and password");
            RestAssured.baseURI = BASE_URL;
            RequestSpecification request = RestAssured.given();
            JSONObject requestBody = new JSONObject();
            requestBody.put("email", "newuser@email.com");
            requestBody.put("password", "password");
            request.header("Content-Type", "application/json");
            response = request.body(requestBody.toString()).post(BASE_URL + port + "/auth/users/register/");
    }

    @Then("I should see a success message")
    public void iShouldSeeASuccessMessage() {
        log.info("Successful Registration");
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}
