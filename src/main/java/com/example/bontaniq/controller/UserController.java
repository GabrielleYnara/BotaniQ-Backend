package com.example.bontaniq.controller;

import com.example.bontaniq.model.Profile;
import com.example.bontaniq.model.User;
import com.example.bontaniq.model.request.LoginRequest;
import com.example.bontaniq.model.response.LoginResponse;
import com.example.bontaniq.security.MyUserDetails;
import com.example.bontaniq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * User Controller for handling user operations including authentication, registration, and profile management.
 *
 * <br>
 * <p> Imported and adapted from <a href="ttps://github.com/GabrielleYnara/habit-tracker
 * ">Habit Tracker</a> </p>
 */
@RestController
@RequestMapping(path = "/auth/users") //http://localhost:9092/auth/users
public class UserController {
    private UserService userService;
    private final Logger logger = Logger.getLogger(UserController.class.getName());
    static HashMap<String, Object> message = new HashMap<>();
    /**
     * Injects dependencies and enables userController to access the resources
     * @param userService The Service responsible for user business logic.
     */
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Extracts user information from context holder
     * @return Current logged in User object
     */
    public static User getCurrentLoggedInUser(){
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder //After jwt is generated, Security Context Holder is created to hold the user's state
                .getContext().getAuthentication().getPrincipal(); // the entire User object, with authentication details
        return userDetails.getUser();
    }

    /**
     * Handles user registration by creating a new user.
     *
     * @param user User object containing the information of the user to be registered.
     * @return The newly created User object.
     */
    @PostMapping(path = "/register/") //http://localhost:9092/auth/users/register/
    public ResponseEntity<?> registerUser(@RequestBody User user){
        User newUser = userService.registerUser(user);
        if (newUser != null){
            message.put("message", "Registration completed.");
            message.put("data", newUser);
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        } else {
            message.put("message", "Unable to register.");
            return new ResponseEntity<>(message, HttpStatus.CONFLICT);
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
            logger.info("User Authenticated.");
            return ResponseEntity.ok(new LoginResponse(jwtToken.get()));
        } else {
            logger.severe("Authentication failed!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse("Authentication failed!"));
        }
    }

//    /**
//     * Retrieves the profile of the currently logged-in user.
//     * @return Profile object
//     */
//    @GetMapping(path="/profile/") //http://localhost:9092/auth/users/profile/
//    public Profile getUserProfile(){
//        return getCurrentLoggedInUser().getProfile();
//    }
//
//    /**
//     * Updates the user's profile, allowing for individual or multiple attribute changes.
//     *
//     * @param profile Profile object with new details.
//     * @return Updated profile.
//     */
//    @PutMapping(path="/profile/") //http://localhost:9092/auth/users/profile/
//    public Profile updateUserProfile(@RequestBody Profile profile){
//        User user = getCurrentLoggedInUser();
//        user.setProfile(profile);
//        return userService.updateUserProfile(user).getProfile();
//    }
}
