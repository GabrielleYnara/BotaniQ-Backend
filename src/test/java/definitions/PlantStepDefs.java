package definitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.logging.Logger;

public class PlantStepDefs extends SetupTestDefs{
    private static final Logger log = Logger.getLogger(PlantStepDefs.class.getName());
    private String name;
    private String type;
    private int plantId;
    private String careTypeDescription;
    private String careTypeFrequency;
    private int careTypeId;
    private LocalDate careDate;
    @When("I create a plant name {string} and type {string}")
    public void iCreateAPlantNameAndType(String name, String type) {
        log.info("I create a plant name " + name + " and type " + type);
        try {
            RestAssured.baseURI = BASE_URL;
            RequestSpecification request = RestAssured.given();
            JSONObject requestBody = new JSONObject();
            requestBody.put("name", name);
            requestBody.put("type", type);
            response = request.body(requestBody.toString()).post(BASE_URL + port + "/gardens/1/plants");
        } catch (Exception e) {
            log.severe("Something went wrong during plant creation. Error: " + e.getMessage());
        }
    }

    @Then("the plant should be created successfully and added to the garden")
    public void thePlantShouldBeCreatedSuccessfullyAndAddedToTheGarden() {
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        log.info("the plant should be created successfully and added to the garden");
    }

    @When("I create a plant with {string} or {string} missing")
    public void iCreateAPlantWithOrMissing(String name, String type) {
        log.info("I create a plant with name or type missing");
        try {
            RestAssured.baseURI = BASE_URL;
            RequestSpecification request = RestAssured.given();
            JSONObject requestBody = new JSONObject();
            if (!name.isEmpty()) {
                requestBody.put("name", name);
            }
            if (!type.isEmpty()) {
                requestBody.put("type", type);
            }
            response = request.body(requestBody.toString()).post(BASE_URL + port + "/gardens/1/plants");
        } catch (Exception e) {
            log.severe("Something went wrong during plant creation with missing info. \nError: " + e.getMessage());
        }
    }

    @Given("I provide a valid plant")
    public void iProvideAValidPlant() {
        log.info("I provide a valid plant");
        this.plantId = 1;
    }

    @When("I request to view a singular plant in the garden")
    public void iRequestToViewASingularPlantInTheGarden() {
        log.info("I request to view a singular plant in the garden");
        try{
            RestAssured.baseURI = BASE_URL;
            RequestSpecification request = RestAssured.given();
            request.headers(createAuthenticatedHeader(token));
            response = request.get(BASE_URL + port + "/gardens/1/plants/" + plantId + "/");
        }catch (Exception e){
            log.severe("Something went wrong while requesting to view a plant.");
        }
    }

    @Then("I should see the plants details")
    public void iShouldSeeThePlantsDetails() {
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        log.info("I should see the plants details");
    }

    @Given("I provide an invalid plant")
    public void iProvideAnInvalidPlant() {
        this.plantId = -1;
    }

    @Given("I provide a {string} description for the care type")
    public void iProvideADescriptionForTheCareType(String description) {
        log.info("I provide " + description + " as a description for the care type.");
        this.careTypeDescription = description;
    }

    @And("I set the frequency to {string}")
    public void iSetTheFrequencyTo(String frequency) {
        log.info("I provide " + frequency + " as a frequency for the care type.");
        this.careTypeFrequency = frequency;
    }

    @When("I attempt to create the care type")
    public void iAttemptToCreateTheCareType() {
        log.info("I attempt to create the care type");
        try{
            RestAssured.baseURI = BASE_URL;
            RequestSpecification request = RestAssured.given();
            request.headers(createAuthenticatedHeader(token));
            JSONObject requestBody = new JSONObject();
            requestBody.put("description", careTypeDescription);
            requestBody.put("frequency", careTypeFrequency);
            response = request.post(BASE_URL + port + "/gardens/1/plants/" + plantId + "/care/");
        }catch (Exception e){
            log.severe("Something went wrong while creating a plant care type.");
        }
    }

    @Then("the application should save the care type and frequency")
    public void theApplicationShouldSaveTheCareTypeAndFrequency() {
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Then("the application should not save the care type")
    public void theApplicationShouldNotSaveTheCareType() {
        Assert.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @And("I provide a valid care type")
    public void iProvideAValidCareType() {
        log.info("I provide a valid care type");
        try{
            careTypeId = 1;
            RestAssured.baseURI = BASE_URL;
            RequestSpecification request = RestAssured.given();
            request.headers(createAuthenticatedHeader(token));
            response = request.get(BASE_URL + port + "/gardens/1/plants/" + plantId + "/care/" + careTypeId + "/");
            //ToDo: refactor end-points, elegant solution.
        } catch (Exception e){
            log.severe("Something went wrong while verifying a valid care.");
        }
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        log.info("The care type is valid.");
    }

    @Given("I specify the date care was administered")
    public void iSpecifyTheDateCareWasAdministered() {
        this.careDate = LocalDate.parse("2023-10-21");
        log.info("I specify the date care was administered");
    }

    @When("I request to register the care event")
    public void iRequestToRegisterTheCareEvent() {
        log.info("I request to register the care event.");
        try{
            RestAssured.baseURI = BASE_URL;
            RequestSpecification request = RestAssured.given();
            request.headers(createAuthenticatedHeader(token));
            JSONObject requestBody = new JSONObject();
            requestBody.put("date", careDate);
            response = request.body(requestBody.toString()).post(BASE_URL + port + "/gardens/1/plants/" + plantId + "/care/" + careTypeId + "/");
        } catch (Exception e){
            log.severe("Something went wrong while registering a plant care.");
        }
    }

    @Then("the application should save the care event")
    public void theApplicationShouldSaveTheCareEvent() {
        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Given("The date care was administered is missing")
    public void theDateCareWasAdministeredIsMissing() {
        this.careDate = null;
    }

    @Then("the application should not save the care event")
    public void theApplicationShouldNotSaveTheCareEvent() {
        Assert.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }
}
