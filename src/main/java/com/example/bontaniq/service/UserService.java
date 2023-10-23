package com.example.bontaniq.service;

import com.example.bontaniq.controller.UserController;
import com.example.bontaniq.exception.exception.InformationExistException;
import com.example.bontaniq.model.Profile;
import com.example.bontaniq.model.User;
import com.example.bontaniq.model.request.LoginRequest;
import com.example.bontaniq.repository.ProfileRepository;
import com.example.bontaniq.repository.UserRepository;
import com.example.bontaniq.security.JWTUtils;
import com.example.bontaniq.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

/**
 * Represents the User Service, responsible for housing business logic related to users.<br>
 *
 * This class serves as an intermediary between the controller and the repository,
 * invoking the repository to perform CRUD operations on users.
 * */
@Service
public class UserService {
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    private Logger logger = Logger.getLogger(UserController.class.getName());

    private final PasswordEncoder passwordEncoder;
    private final JWTUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    /**
     * Injects dependencies and enables userService to access the resources.
     *
     * @param userRepository        The repository for user-related CRUD operations.
     * @param profileRepository     The repository for profile-related CRUD operations.
     * @param passwordEncoder       The encoder used for password hashing.
     * @param jwtUtils              The utility class for JWT token generation and validation.
     * @param authenticationManager Manages authentication within the security context.
     */
    @Autowired
    public UserService(UserRepository userRepository, ProfileRepository profileRepository, @Lazy PasswordEncoder passwordEncoder,
                       JWTUtils jwtUtils,
                       @Lazy AuthenticationManager authenticationManager) { //@LAZY - loads as needed
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Registers a new user, assigns
     * @param user User object containing details.
     * @return The registered User.
     * @throws InformationExistException If email is already registered.
     */
    public User registerUser(User user){
        Optional<User> userOptional = Optional.ofNullable(userRepository.findUserByEmailAddress(user.getEmailAddress())); //checks if email address already exists in database
        if (userOptional.isEmpty()){ // email not registered yet
            logger.info("Email provided is not registered yet.");
            user.setPassword(passwordEncoder.encode(user.getPassword())); //encode password given
            profileRepository.save(user.getProfile());
            return userRepository.save(user);
        } else {
            logger.severe("user with email address " + user.getEmailAddress() + " already exist.");
            throw new InformationExistException("user with email address " + user.getEmailAddress() + " already exist.");
        }
    }

    /**
     * Finds a User entity based on the provided email address.
     *
     * @param emailAddress The email address to search for.
     * @return The User entity,
     *         or null if no matching user is found.
     */
    public User findUserByEmailAddress(String emailAddress){
        return userRepository.findUserByEmailAddress(emailAddress);
    }

    /**
     * Authenticate a user based on the provided login request.
     *
     * @param loginRequest The login request with email and password.
     * @return Optional JWT token if authentication is successful;
     *         otherwise, an empty Optional.
     */
    public Optional<String> loginUser(LoginRequest loginRequest){
        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(loginRequest.getEmailAddress(), loginRequest.getPassword());
        try{
            logger.info("Service: Attempt to login.");
            Authentication authentication = authenticationManager.authenticate((authenticationToken)); //authenticate the user
            SecurityContextHolder.getContext().setAuthentication(authentication); //set security context
            MyUserDetails myUserDetails = ( MyUserDetails ) authentication.getPrincipal(); //get user details from authenticated object
            return Optional.of(jwtUtils.generateJwtToken(myUserDetails)); // generate a token for the authenticated user
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
