package definitions;

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

public class UserLoginDefs extends SetupTestDefs{
    private static Response response;
    private static final Logger log = Logger.getLogger(UserLoginDefs.class.getName());
    @When("I provide a valid email and password pair")
    public void iProvideAValidEmailAndPasswordPair() throws JSONException {
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        JSONObject requestBody = new JSONObject();
        requestBody.put("email", "newuser@email.com");
        requestBody.put("password", "password");
        request.header("Content-Type", "application/json");
        response = request.body(requestBody.toString()).post(BASE_URL + port + "/auth/users/login/");
    }

    @Then("I should be redirected to the home page")
    public void iShouldBeRedirectedToTheHomePage() {
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        String currentUrl = BASE_URL + port + "/home/";
        Assert.assertEquals(BASE_URL + port + "/home/", currentUrl);
    }

}
