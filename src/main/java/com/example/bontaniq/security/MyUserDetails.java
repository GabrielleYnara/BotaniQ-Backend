package com.example.bontaniq.security;

import com.example.bontaniq.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;

/**
 * Holds user information for authorization and authentication. <br>
 * Implements the UserDetails interface from Spring Security.
 *
 * <p> Imported and adapted from <a href="ttps://github.com/GabrielleYnara/habit-tracker
">Habit Tracker</a> </p>*/
public class MyUserDetails implements UserDetails {
    /**
     * Represents the associated User object.
     */
    private final User user;

    /**
     * Initializes a new instance of MyUserDetails with the provided User object.
     *
     * @param user The User object containing user's details.
     */
    public MyUserDetails(User user) {
        this.user = user;
    }

    /**
     * Returns the User.
     * @return
     */
    public User getUser() {
        return user;
    }

    /**
     * Returns a collection of authorities (permissions) granted to the user.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new HashSet<>();
    }

    /**
     * Returns the user's password.
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     * Returns the user's email address.
     */
    @Override
    public String getUsername() {
        return user.getEmailAddress();
    }

    /**
     * @return true if Account is not expired,
     *         false otherwise.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * @return true if Account is not locked,
     *         false otherwise.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * @return true if Credentials are not expired,
     *         false otherwise.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * @return true if Account is enabled,
     *         false otherwise.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
