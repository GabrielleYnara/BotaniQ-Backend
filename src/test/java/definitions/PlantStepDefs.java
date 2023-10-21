package definitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.http.HttpStatus;

import java.util.logging.Logger;

public class PlantStepDefs extends SetupTestDefs{
    private static final Logger log = Logger.getLogger(PlantStepDefs.class.getName());
    private String name;
    private String type;
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
}
