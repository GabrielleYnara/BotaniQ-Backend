package com.example.bontaniq.security;

import com.example.bontaniq.model.User;
import com.example.bontaniq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Responsible for loading user-specific data during the authentication process. <br>
 * It implements the UserDetailsService interface from Spring Security.
 *
 *  <p> Imported and adapted from <a href="ttps://github.com/GabrielleYnara/habit-tracker
 * ">Habit Tracker</a> </p>*/
@Service
public class MyUserDetailsService implements UserDetailsService {
    private UserService userService;

    /**
     * Injects dependency with userService and allows to use its resources.
     * @param userService The User Service, responsible for user-related business logic.
     */
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Loads a user's data based on their email address and constructs a new MyUserDetails instance.
     *
     * @param username The email address of the user to be loaded.
     * @return A new instance of MyUserDetails containing user details.
     * @throws UsernameNotFoundException if the user with the given email address is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findUserByEmailAddress(username); //emailAddress
        return new MyUserDetails(user);
    }
}
