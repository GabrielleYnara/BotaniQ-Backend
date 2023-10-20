package definitions;

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
    @Given("I provide a description {string}")
    public void iProvideADescription(String description) {
        log.info("I provide a description.");
        this.description = description;
    }

    @And("I provide additional notes {string}")
    public void iProvideAdditionalNotes(String notes) {
        log.info("I provide additional notes");
        this.notes = notes;
    }

    @When("I attempt to create a garden just with a description")
    public void iAttemptToCreateAGardenJustWithADescription() throws JSONException {
        log.info("I attempt to create a garden just with a description.");
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        JSONObject requestBody = new JSONObject();
        requestBody.put("description", description);
        request.headers(createAuthenticatedHeader(token));
        response = request.body(requestBody.toString()).post(BASE_URL + port + "/gardens/");
    }

    @When("I attempt to create a garden with a description and notes")
    public void iAttemptToCreateAGardenWithADescriptionAndNotes() throws JSONException {
        log.info("I attempt to create a garden with a description and notes.");
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
        log.info("the garden should be saved successfully");
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @And("no other garden has the description {string}")
    public void noOtherGardenHasTheDescription(String description) {
        Assert.assertEquals(false, aGardenWithDescriptionAlreadyExists(description));

    }

    @Given("a garden with description {string} already exists")
    public boolean aGardenWithDescriptionAlreadyExists(String description) {
        log.info("a garden with given description already exists");
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.headers(createAuthenticatedHeader(token));
        response = request.queryParam("description", description).get(BASE_URL + port + "/gardens");
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        if (response.getStatusCode() == 200){
            return true;
        } else {
            return false;
        }
    }

    @When("I attempt to create a duplicated garden")
    public void iAttemptToCreateADuplicatedGarden() throws JSONException {
        log.info("I attempt to create a duplicated garden.");
        this.iAttemptToCreateAGardenJustWithADescription();
    }

    @Then("I should see an error message {string}")
    public void iShouldSeeAnErrorMessage(String message) {
        Assert.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }
}
