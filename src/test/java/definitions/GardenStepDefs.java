package definitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @see <a href="https://webkul.com/blog/parametrization-in-cucumber/">Parametrization in Cucumber</a>
 */
public class GardenStepDefs extends SetupTestDefs{
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

    @When("I attempt to create a garden with a description {string} and notes {string}")
    public void iAttemptToCreateAGardenWithADescriptionAndNotes(String description, String notes) {
        log.info("I attempt to create a garden just with a description.");
        try {
            RestAssured.baseURI = BASE_URL;
            RequestSpecification request = RestAssured.given();
            JSONObject requestBody = new JSONObject();
            if (!description.isEmpty()) {
                requestBody.put("description", description);
            }
            if (!notes.isEmpty()){
                requestBody.put("notes", notes);
            }
            request.headers(createAuthenticatedHeader(token));
            response = request.body(requestBody.toString()).post(BASE_URL + port + "/gardens/");
        } catch (Exception e){
            log.severe("Something went wrong when I attempt to create a garden just with a description.\n"
            + "Error: " + e.getMessage());
        }
    }

    @When("I attempt to create a garden with a description and notes")
    public void iAttemptToCreateAGardenWithADescriptionAndNotes() {
        log.info("I attempt to create a garden with a description and notes.");
        try {
            RestAssured.baseURI = BASE_URL;
            RequestSpecification request = RestAssured.given();
            JSONObject requestBody = new JSONObject();
            requestBody.put("description", description);
            requestBody.put("notes", notes);
            request.headers(createAuthenticatedHeader(token));
            response = request.body(requestBody.toString()).post(BASE_URL + port + "/gardens/");
        } catch (Exception e){
            log.severe("Something went wrong when I attempt to create a garden with a description and notes.\n"
                    + "Error: " + e.getMessage());
        }

    }

    @Then("the garden should be saved successfully")
    public void theGardenShouldBeSavedSuccessfully() {
        log.info("the garden was saved successfully");
        Assert.assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
    }

    @Given("a garden with description {string} already exists")
    public boolean aGardenWithDescriptionAlreadyExists(String description) {
        log.info("a garden with given description already exists");
        try{
            RestAssured.baseURI = BASE_URL;
            RequestSpecification request = RestAssured.given();
            request.headers(createAuthenticatedHeader(token));
            response = request.queryParam("description", description).get(BASE_URL + port + "/gardens");
            Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
            if (response.getStatusCode() == 200) {
                return true;
            }
        } catch (Exception e){
            log.severe("Something went wrong when I attempt to get a garden with existing description.\n"
                    + "Error: " + e.getMessage());
        }
        return false;
    }

    @Then("I should see an error message {string}")
    public void iShouldSeeAnErrorMessage(String message) {
        Assert.assertEquals(HttpStatus.CONFLICT.value(), response.getStatusCode());
        log.severe("Error message: " + message);
    }

    @And("I have a garden with description {string} and notes {string}")
    public void iHaveAGardenWithDescriptionAndNotes(String description, String notes) {
        log.info("I have a garden with description " + description  + " and notes " + notes);
        this.description = description;
        this.notes = notes;
    }

    @When("I update the description to {string} and the notes to {string}")
    public void iUpdateTheDescriptionToAndTheNotesTo(String description, String notes) {
        log.info("I update the description to " + description + " and the notes to " + notes);
        try{
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
        } catch (Exception e){
            log.severe("Something went wrong when I attempt to update a garden.\n"
                    + "Error: " + e.getMessage());
        }

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
    public void iUpdateTheGardenWithTheSameDescriptionAndNotes() {
        log.info("I update the garden with the same description and notes");
        try{
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
        } catch (Exception e){
            log.severe("Something went wrong when I attempt to update an unchanged garden.\n"
                    + "Error: " + e.getMessage());
        }
    }

    @When("I request to view the plants of a valid garden")
    public void iRequestToViewThePlantsOfAValidGarden() {
        log.info("Request to retrieve a garden and its plants.");
        try{
            RestAssured.baseURI = BASE_URL;
            RequestSpecification request = RestAssured.given();
            request.headers(createAuthenticatedHeader(token));
            response = request.get(BASE_URL + port + "/gardens/1/");
        } catch (Exception e){
            log.severe("Something went wrong when I request to view the plants in the garden.\n"
                    + "Error: " + e.getMessage());
        }

    }

    @Then("I should see a list of plants associated with the garden")
    public void iShouldSeeAListOfPlantsAssociatedWithTheGarden() {

        JsonPath jsonPath = response.jsonPath();
        String message = jsonPath.get("message");
        Object garden = jsonPath.get("data");
//        List<Map<String, String>> plants = garden.;
//        Assert.assertFalse(plants.isEmpty());
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        log.info(message);
        log.info(garden.toString());
    }

    @Given("I provide an invalid garden")
    public void iProvideAnInvalidGarden() {
        log.info("I provide an invalid garden");
        try{
            RestAssured.baseURI = BASE_URL;
            RequestSpecification request = RestAssured.given();
            request.headers(createAuthenticatedHeader(token));
            response = request.get(BASE_URL + port + "/gardens/invalid-garden/");
        } catch (Exception e){
            log.severe("Something went wrong when I provide an invalid garden.\n"
                    + "Error: " + e.getMessage());
        }
    }
}
