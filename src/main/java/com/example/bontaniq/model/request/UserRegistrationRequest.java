package com.example.bontaniq.model.request;

import com.example.bontaniq.model.Profile;
import com.example.bontaniq.model.User;
/**
 * Represents the data structure for handling user registration HTTP request body.<br>
 * This model captures the necessary information required for user registration, combining both user and the associated profile.
 */
public class UserRegistrationRequest {
    /**
     * User details, including email and password, required for registration.
     */
    private User user;
    /**
     * Profile details associated with the user, like first name, last name, and bio.
     */
    private Profile profile;

    public User getUser() {
        return user;
    }

    public Profile getProfile() {
        return profile;
    }
}
