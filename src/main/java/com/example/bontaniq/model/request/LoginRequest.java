package com.example.bontaniq.model.request;

/**
 * Represents the data structure for handling login HTTP request body.<br>
 * This model captures the necessary information required for user authentication, the email address and password.
 */
public class LoginRequest {

    /**
     * The email of the user attempting to log in.
     */
    private String emailAddress;

    /**
     * The password provided by the user for authentication.
     */
    private String password;

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }
}
