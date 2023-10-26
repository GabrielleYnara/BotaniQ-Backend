package com.example.bontaniq.model.response;

import com.example.bontaniq.model.User;

public class LoginResponse {

    public String jwt;
    public User user;

    public LoginResponse(String jwt) {
        this.jwt = jwt;
    }

    public LoginResponse(String jwt, User user) {
        this.jwt = jwt;
        this.user = user;
    }
}
