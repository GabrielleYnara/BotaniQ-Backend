package definitions;

import definitions.user.UserLoginDefs;
import definitions.user.UserProfileDefs;
import definitions.user.UserRegistrationDefs;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.http.HttpStatus;

import java.util.logging.Logger;

public class GardenStepDefs extends  SetupTestDefs{
    private static Response response;
    private static final Logger log = Logger.getLogger(GardenStepDefs.class.getName());

    private String description;
    private String notes;
    @Given("I provide a unique description {string}")
    public void iProvideAUniqueDescription(String description) {
        log.info("SCENARIO: Successful garden creation with a unique description.");
        log.info("I provide a unique description.");
        this.description = description;
    }

    @When("I attempt to create a garden just with a description")
    public void iAttemptToCreateAGardenJustWithADescription() throws JSONException {
        log.info("I attempt to create the garden");
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        JSONObject requestBody = new JSONObject();
        requestBody.put("description", description);
        requestBody.put("notes", notes);
        request.headers(createAuthenticatedHeader(token));
        response = request.body(requestBody.toString()).post(BASE_URL + port + "/gardens/");
    }

    @Then("the garden should be saved successfully")
    public void theGardenShouldBeSavedSuccessfully() {
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}
