package com.example.bontaniq.model.response;

import com.example.bontaniq.model.User;

/**
 * Represents the data structure for the login response sent to the client after successful authentication.<br>
 * This model captures the JWT token and, optionally, the user details.
 */
public class LoginResponse {

    /**
     * The JSON Web Token generated after successful authentication.<br>
     * Or error message after failed authentication.
     */
    public String jwt;

    /**
     * The details of the authenticated user.
     */
    public User user;

    public LoginResponse(String jwt) {
        this.jwt = jwt;
    }

    public LoginResponse(String jwt, User user) {
        this.jwt = jwt;
        this.user = user;
    }
}
