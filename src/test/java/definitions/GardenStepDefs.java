package definitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @see <a href="https://webkul.com/blog/parametrization-in-cucumber/">Parametrization in Cucumber</a>
 */
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

    @And("I have a garden with description {string} and notes {string}")
    public void iHaveAGardenWithDescriptionAndNotes(String description, String notes) {
        log.info("I have a garden with description " + description  + " and notes " + notes);
        this.description = description;
        this.notes = notes;
    }

    @When("I update the description to {string} and the notes to {string}")
    public void iUpdateTheDescriptionToAndTheNotesTo(String description, String notes) throws JSONException {
        log.info("I update the description to " + description + " and the notes to " + notes);
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        JSONObject requestBody = new JSONObject();
        if (!description.isEmpty()){
            requestBody.put("description", description);
        }
        if (!notes.isEmpty()){
            requestBody.put("notes", notes);
        }
        request.headers(createAuthenticatedHeader(token));
        response = request.body(requestBody.toString()).put(BASE_URL + port + "/gardens/1/");
    }

    @And("the new information is different from the original")
    public void theNewInformationIsDifferentFromTheOriginal() {
        log.info("the new information is different from the original");
        Assert.assertNotEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Then("the garden should be updated successfully")
    public void theGardenShouldBeUpdatedSuccessfully() {
        log.info("the garden should be updated successfully");
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @When("I update the garden with the same description and notes")
    public void iUpdateTheGardenWithTheSameDescriptionAndNotes() throws JSONException {
        log.info("I update the garden with the same description and notes");
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        JSONObject requestBody = new JSONObject();
        if (!description.isEmpty()){
            requestBody.put("description", description);
        }
        if (!notes.isEmpty()){
            requestBody.put("notes", notes);
        }
        request.headers(createAuthenticatedHeader(token));
        response = request.body(requestBody.toString()).put(BASE_URL + port + "/gardens/1/");
    }

    @And("I have a valid garden")
    public void iHaveAValidGarden() {
        log.info("I have a valid garden");
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.headers(createAuthenticatedHeader(token));
        response = request.get(BASE_URL + port + "/gardens/1/");
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }//ToDo: assign the response to a garden obj and use it in the @when statement

    @When("I request to view the plants in the garden")
    public void iRequestToViewThePlantsInTheGarden() {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.headers(createAuthenticatedHeader(token));
        response = request.get(BASE_URL + port + "/gardens/1/plants/");
    }

    @Then("I should see a list of plants associated with the garden")
    public void iShouldSeeAListOfPlantsAssociatedWithTheGarden() {
        List<Map<String, String>> plants = JsonPath.from(String.valueOf(response.getBody())).get("data");
        Assert.assertFalse(plants.isEmpty());
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Given("I provide an invalid garden")
    public void iProvideAnInvalidGarden() {
        log.info("I provide an invalid garden");
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.headers(createAuthenticatedHeader(token));
        response = request.get(BASE_URL + port + "/gardens/invalid-garden/");
    }
}
