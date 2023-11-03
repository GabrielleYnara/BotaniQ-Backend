package com.example.bontaniq.controller;

import java.util.HashMap;
import java.util.logging.Logger;

/**
 * Provides common properties and behavior for controllers to access shared resources or functionalities.
 */
public class ControllerSharedResources {

    /**
     * Centralized http request response structure for consistency across different controllers.
     */
    static HashMap<String, Object> requestResponse = new HashMap<>();

    /**
     * Logger for recording activities and operations within controllers.
     */
    protected Logger logger = Logger.getLogger(getClass().getName());
}
