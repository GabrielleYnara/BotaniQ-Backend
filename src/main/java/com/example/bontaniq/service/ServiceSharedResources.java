package com.example.bontaniq.service;

import com.example.bontaniq.model.User;
import com.example.bontaniq.security.MyUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.logging.Logger;

/**
 * Provides utility methods that can be used by multiple services to access shared resources or functionalities.
 */
public class ServiceSharedResources {
    /**
     * Logger instance for logging activities within the service.
     */
    protected Logger logger = Logger.getLogger(getClass().getName());

    /**
     * Extracts user information from context holder.
     * @return Current logged in User object
     */
    public User getCurrentLoggedInUser(){
        logger.info("Retrieving the current logged in User");
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder //After jwt is generated, Security Context Holder is created to hold the user's state
                .getContext().getAuthentication().getPrincipal(); // the entire User object, with authentication details
        return userDetails.getUser();
    }
}
