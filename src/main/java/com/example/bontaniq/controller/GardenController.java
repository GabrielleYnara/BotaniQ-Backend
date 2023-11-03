package com.example.bontaniq.controller;

import com.example.bontaniq.model.Garden;
import com.example.bontaniq.service.GardenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Handles HTTP requests related to garden operations.
 */
@RestController
@RequestMapping(path="/gardens") //http://localhost:9092/gardens
public class GardenController extends ControllerSharedResources {
    private final GardenService gardenService;

    /**
     * Injects dependencies and enables gardenController to access the resources.
     * @param gardenService The Service responsible for garden business logic operations.
     */
    @Autowired
    public GardenController(GardenService gardenService) {
        this.gardenService = gardenService;
    }

    /**
     * Creates a new garden and associates it currently logged-in user.
     *
     * @param garden The garden object containing the details for creation.
     * @return A ResponseEntity containing the created garden or an error message.
     */

    @PostMapping(path = "/") //http://localhost:9092/gardens/
    public ResponseEntity<?> createGarden(@RequestBody Garden garden){
        logger.info("Attempt to create a new Garden.");
        Optional<Garden> newGarden = gardenService.createGarden(garden);
        if (newGarden.isPresent()){
            requestResponse.put("message", "Garden creation completed.");
            requestResponse.put("data", newGarden);
            return new ResponseEntity<>(requestResponse, HttpStatus.CREATED);
        } else {
            requestResponse.put("message", "Garden creation failed!");
            logger.severe(requestResponse.get("message").toString());
            return new ResponseEntity<>(requestResponse, HttpStatus.CONFLICT);
        }
    }

    /**
     * Retrieves a specific garden by its id.
     * @param gardenId The garden id to retrieve.
     * @return A ResponseEntity containing the requested garden or an error message.
     */
    @GetMapping(path = "/{gardenId}/") //http://localhost:9092/gardens/1/
    public ResponseEntity<?> getGarden(@PathVariable(value = "gardenId") Long gardenId){
        logger.info("Attempt to retrieve a garden with id " + gardenId);
        Optional<Garden> garden = gardenService.getGardenById(gardenId);
        if (garden.isPresent()){
            requestResponse.put("message", "Garden retrieved successfully.");
            requestResponse.put("data", garden);
            return new ResponseEntity<>(requestResponse, HttpStatus.OK);
        } else {
            requestResponse.put("message", "Garden not found!");
            logger.severe(requestResponse.get("message").toString());
            return new ResponseEntity<>(requestResponse, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Retrieves all the gardens associated with the currently logged-in user.
     * @return A ResponseEntity containing the list of garden records or an error message.
     */
    @GetMapping(path = "/") //http://localhost:9092/gardens/
    public ResponseEntity<?> getAllGardens(){
        logger.info("Attempt to retrieve a garden list.");
        List<Garden> gardenList = gardenService.getAllGardens();
        if (!gardenList.isEmpty()){
            requestResponse.put("message", "Garden list retrieved successfully.");
            requestResponse.put("data", gardenList);
            return new ResponseEntity<>(requestResponse, HttpStatus.OK);
        } else {
            requestResponse.put("message", "Garden list empty!");
            logger.severe(requestResponse.get("message").toString());
            return new ResponseEntity<>(requestResponse, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Updates the garden, allowing for individual or multiple attribute changes.
     *
     * @param garden Garden object with new details.
     * @param gardenId The garden id targeted.
     * @return A ResponseEntity containing the updated garden records or an error message.
     */
    @PutMapping(path = "/{gardenId}/") //http://localhost:9092/gardens/1/
    public ResponseEntity<?> updateGarden(@RequestBody Garden garden, @PathVariable(value = "gardenId") Long gardenId) throws IllegalAccessException {
        logger.info("Attempt to update garden.");
        Optional<Garden> updatedGarden = gardenService.updateGarden(garden, gardenId);
        if (updatedGarden.isPresent()){
            requestResponse.put("message", "Garden successfully updated");
            requestResponse.put("date", updatedGarden);
            return new ResponseEntity<>(requestResponse, HttpStatus.OK);
        } else {
            requestResponse.put("message", "Garden update failed!");
            return new ResponseEntity<>(requestResponse, HttpStatus.CONFLICT);
        }
    }
}
