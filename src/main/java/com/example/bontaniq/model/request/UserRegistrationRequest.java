package com.example.bontaniq.model.request;

import com.example.bontaniq.model.Profile;
import com.example.bontaniq.model.User;

public class UserRegistrationRequest {
    private User user;
    private Profile profile;

    public User getUser() {
        return user;
    }

    public Profile getProfile() {
        return profile;
    }
}
