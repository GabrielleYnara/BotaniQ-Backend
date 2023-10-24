package definitions;

import com.example.bontaniq.model.Profile;
import com.example.bontaniq.model.User;
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
import org.springframework.http.*;

import java.util.logging.Logger;

/**
 * @see <a href="https://cucumber.io/docs/guides/10-minute-tutorial/#write-a-scenario">Cucumber Docs - Write a Scenario</a>
 */
public class UserStepDefs extends SetupTestDefs {
    private static Response response;
    private static User user = new User();
    private static final Logger log = Logger.getLogger(UserStepDefs.class.getName());

    @When("I enter a unique email and additional user details")
    public void iEnterAUniqueEmailAndAdditionalUserDetails(){
        log.info("User Registration - I enter a unique email and additional user details.");
        try {
            RestAssured.baseURI = BASE_URL;
            RequestSpecification request = RestAssured.given();

            JSONObject userObject = new JSONObject();
            userObject.put("emailAddress", "newuser@email.com");
            userObject.put("password", "password");

            JSONObject profileObject = new JSONObject();
            profileObject.put("firstName", "Test");
            profileObject.put("lastName", "User");

            JSONObject requestBody = new JSONObject();

            requestBody.put("profile", profileObject);
            requestBody.put("user", userObject);

            request.header("Content-Type", "application/json");
            log.info("requestBody.toString(): " + requestBody);
            response = request.body(requestBody.toString()).post(BASE_URL + port + "/auth/users/register/");
        } catch (Exception e){
            log.severe("Something went wrong during registration.\n"
                + "Error: " + e.getMessage());
        }
    }

    @Then("I should see a success message")
    public void iShouldSeeASuccessMessage() {
        log.info("Successful Registration.");
        JsonPath jsonPath = response.jsonPath();
        Object newUser = jsonPath.get("data");
        log.info("User: " + newUser);
        Assert.assertEquals(HttpStatus.CREATED.value(), response.getStatusCode());
    }

    @When("I enter an email already taken")
    public void iEnterAnEmailAlreadyTaken() {
        log.info("User Registration - I enter an email already taken.");
        this.iEnterAUniqueEmailAndAdditionalUserDetails();
    }

    @Then("I should see an error message")
    public void iShouldSeeAnErrorMessage() {
        log.info("THEN - I should see an error message.");
        Assert.assertEquals(HttpStatus.CONFLICT.value(), response.getStatusCode());
    }
    @Given("I am logged in")
    public void iAmLoggedIn() throws JSONException {
        log.info("GIVEN - I am logged in");
        try {
            RestAssured.baseURI = BASE_URL;
            RequestSpecification request = RestAssured.given();
            request.header("Content-Type", "application/json");
            JSONObject requestBody = new JSONObject();
            requestBody.put("emailAddress", "gabrielleynara@ymail.com");
            requestBody.put("password", "gaby1234");
            response = request.body(requestBody.toString()).post(BASE_URL + port + "/auth/users/login/");
            token = response.jsonPath().getString("jwt");
            log.info("TOKEN " + token);
            Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
        } catch (Exception e){
            log.severe("Something went wrong during login.");
            log.severe(e.getMessage());
        }
    }

    @When("I enter my first name {string}, last name {string}, and or bio {string}")
    public void iEnterMyFirstNameLastNameAndOrBio(String firstName, String lastName, String bio) {
        log.info("WHEN - I enter my first name, last name, and or bio");
        try {
            RestAssured.baseURI = BASE_URL;
            RequestSpecification request = RestAssured.given();
            JSONObject requestBody = new JSONObject();
            if (!firstName.isEmpty()){
                requestBody.put("firstName", firstName);
            }
            if (!lastName.isEmpty()){
                requestBody.put("lastName", lastName);
            }
            if (!bio.isEmpty()) {
                requestBody.put("bio", "Love gardening and caring for my fresh herbs");
            }
            request.headers(createAuthenticatedHeader(token));
            response = request.body(requestBody.toString()).put(BASE_URL + port + "/auth/users/profile/");
        } catch (Exception e ){
            log.severe("Something went wrong during user's profile update.\n"
                + "Error: " + e.getMessage());
        }
    }

    @When("the server encounters an error")
    public void theServerEncountersAnError() {
        log.info("WHEN - the server encounters an error");
        RestAssured.baseURI = BASE_URL;
        RequestSpecification request = RestAssured.given();
        request.headers(createAuthenticatedHeader(token));
        // Sending request to an invalid endpoint to simulate server error
        response = request.body(new JSONObject().toString()).put(BASE_URL + port + "/invalid-endpoint/");
    }

    @Then("I should see the profile update failed")
    public void iShouldSeeTheProfileUpdateFailed() {
        log.info("THEN - I should see the profile update failed");
        Assert.assertNotEquals(HttpStatus.OK, response.getStatusCode()); //indicating an error occurred.
    }

    @Given("I am a registered user")
    public void iAmARegisteredUser() throws JSONException {
//        log.info("GIVEN - I am already registered.");
//        RestAssured.baseURI = BASE_URL;
//        RequestSpecification request = RestAssured.given();
//        JSONObject requestBody = new JSONObject();
//        requestBody.put("email", "newuser@email.com");
//        requestBody.put("password", "password");
//        request.header("Content-Type", "application/json");
//        response = request.body(requestBody.toString()).post(BASE_URL + port + "/auth/users/register/");
//        Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        user.setEmailAddress("gabrielleynara@ymail.com");
        user.setPassword("gaby1234");
    }
    @When("I provide a valid email and password pair")
    public void iProvideAValidEmailAndPasswordPair() {
        log.info("I provide a valid email and password pair and attempt to login.");
        try{
            RestAssured.baseURI = BASE_URL;
            RequestSpecification request = RestAssured.given();
            request.header("Content-Type", "application/json");
            JSONObject requestBody = new JSONObject();
            requestBody.put("emailAddress", user.getEmailAddress());
            requestBody.put("password", user.getPassword());
            response = request.body(requestBody.toString()).post(BASE_URL + port + "/auth/users/login/");
        } catch (Exception e){
            log.severe("Something went wrong during login test.\n"
                + "Error: " + e.getMessage());
        }
    }

    @Then("I should be authorized")
    public void iShouldBeAuthorized() {
        log.info("User logged in.");
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }

    @When("I provide an incorrect email or password")
    public void iProvideAnIncorrectEmailOrPassword() throws JSONException {
        log.info("I provide an invalid email and password pair and attempt to login.");
        try{
            RestAssured.baseURI = BASE_URL;
            RequestSpecification request = RestAssured.given();
            request.header("Content-Type", "application/json");
            JSONObject requestBody = new JSONObject();
            requestBody.put("emailAddress", user.getEmailAddress());
            requestBody.put("password", "wrong-password");
            response = request.body(requestBody.toString()).post(BASE_URL + port + "/auth/users/login/");
        } catch (Exception e){
            log.severe("Something went wrong during login test.\n"
                    + "Error: " + e.getMessage());
        }
    }

    @Then("I should see a failed login message")
    public void iShouldSeeAFailedLoginMessage() {
        log.severe("Login failed! Invalid email or password.");
        Assert.assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatusCode());
    }

    @Then("The profile is updated and I see a success message")
    public void theProfileIsUpdatedAndISeeASuccessMessage() {
        JsonPath jsonPath = response.jsonPath();
        String message = jsonPath.get("message");
        Object updatedUser = jsonPath.get("user");
        log.info(message);
        log.info(updatedUser.toString());
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
    }
}
