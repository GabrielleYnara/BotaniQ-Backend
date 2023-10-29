package com.example.bontaniq.controller;

import com.example.bontaniq.model.Profile;
import com.example.bontaniq.model.User;
import com.example.bontaniq.model.request.LoginRequest;
import com.example.bontaniq.model.request.UserRegistrationRequest;
import com.example.bontaniq.model.response.LoginResponse;
import com.example.bontaniq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * User Controller for handling user operations including authentication, registration, and profile management.
 *
 * <br>
 * <p> Imported and adapted from <a href="ttps://github.com/GabrielleYnara/habit-tracker
 * ">Habit Tracker</a> </p>
 */
@RestController
@RequestMapping(path = "/auth/users") //http://localhost:9092/auth/users
public class UserController extends ControllerSharedResources {
    private UserService userService;

    /**
     * Injects dependencies and enables userController to access the resources
     * @param userService The Service responsible for user business logic.
     */
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }



    /**
     * Handles user registration by creating a new user.
     *
     * @param userRegistrationRequest userRegistrationRequest object containing user and its profile to be registered.
     * @return The newly created User object.
     */
    @PostMapping(path = "/register/") //http://localhost:9092/auth/users/register/
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationRequest userRegistrationRequest){
        logger.info("Initiating User Registration.");
        User user = userRegistrationRequest.getUser();
        user.setProfile(userRegistrationRequest.getProfile());
        User newUser = userService.registerUser(user);
        if (newUser != null){
            requestResponse.put("message", "Registration completed.");
            requestResponse.put("data", newUser);
            return new ResponseEntity<>(requestResponse, HttpStatus.CREATED);
        } else {
            requestResponse.put("message", "Unable to register.");
            return new ResponseEntity<>(requestResponse, HttpStatus.CONFLICT);
        }
    }

    /**
     * Handles user authentication by generating a JWT token if the credentials are valid.
     *
     * @param loginRequest The LoginRequest containing the user's login credentials.
     * @return A ResponseEntity with a LoginResponse, which includes the JWT token if successful,
     *         or an error message otherwise.
     */
    @PostMapping(path="/login/") //http://localhost:9092/auth/users/login/
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        logger.info("User attempt to login.");
        Optional<String> jwtToken = userService.loginUser(loginRequest);

        if (jwtToken.isPresent()){
            LoginResponse loginResponse = new LoginResponse(jwtToken.get(), userService.getCurrentLoggedInUser());
            logger.info("User Authenticated.");
            return ResponseEntity.ok(loginResponse);
        } else {
            logger.severe("Authentication failed!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse("Authentication failed!"));
        }
    }

    /**
     * Updates the user's profile, allowing for individual or multiple attribute changes.
     *
     * @param profile Profile object with new details.
     * @return Updated profile.
     */
    @PutMapping(path="/profile/") //http://localhost:9092/auth/users/profile/
    public ResponseEntity<?> updateUserProfile(@RequestBody Profile profile) throws IllegalAccessException {
        logger.info("Request to update user's profile");
        Optional<User> updatedUser = userService.updateUserProfile(profile);
        if(updatedUser.isPresent()){
            requestResponse.put("message","Successful profile update!");
            requestResponse.put("user", updatedUser.get());
            return new ResponseEntity<>(requestResponse, HttpStatus.OK);
        } else {
            requestResponse.put("message","Profile update failed!");
            return new ResponseEntity<>(requestResponse, HttpStatus.CONFLICT);
        }

    }
}
