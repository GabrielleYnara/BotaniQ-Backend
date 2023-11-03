package com.example.bontaniq.controller;

import com.example.bontaniq.model.Plant;
import com.example.bontaniq.service.CareTrackService;
import com.example.bontaniq.service.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Handles HTTP requests related to plant operations within a garden.
 */
@Controller
@RequestMapping(path="/gardens/{gardenId}/plants") // localhost:9092/gardens/{gardenId}/plants
public class PlantController extends ControllerSharedResources {
    private final PlantService plantService;
    private final CareTrackService careTrackService;

    /**
     * Injects dependencies and enables plantController to access the resources.
     *
     * @param plantService The Service responsible for plant business logic operations.
     * @param careTrackService The Service responsible for care track business logic operations.
     */
    @Autowired
    public PlantController(PlantService plantService, CareTrackService careTrackService) {
        this.plantService = plantService;
        this.careTrackService = careTrackService;
    }

    /**
     * Creates a new plant and associates it with an existing garden.
     *
     * @param plant The plant object containing the details for creation.
     * @param gardenId The garden id to associate with the new plant.
     * @return A ResponseEntity containing the created plant or an error message.
     */
    @PostMapping(path = "/") //http://localhost:9092/gardens/{gardenId}/plants/
    public ResponseEntity<?> createPlant(@RequestBody Plant plant, @PathVariable(value = "gardenId") Long gardenId){
        logger.info("Attempt to create a new Plant.");
        Optional<Plant> newPlant = plantService.createPlant(plant, gardenId);
        if (newPlant.isPresent()){
            requestResponse.put("message", "Plant saved successfully.");
            requestResponse.put("data", newPlant);
            return new ResponseEntity<>(requestResponse, HttpStatus.CREATED);
        } else {
            requestResponse.put("message", "Plant creation failed!");
            logger.severe(requestResponse.get("message").toString());
            return new ResponseEntity<>(requestResponse, HttpStatus.CONFLICT);
        }
    }

    /**
     * Retrieves a specific plant by its ID within a garden.
     *
     * @param gardenId The garden id where the plant is located.
     * @param plantId The plant id to retrieve.
     * @return A ResponseEntity containing the requested plant or an error message.
     */
    @GetMapping(path = "/{plantId}/") //http://localhost:9092/gardens/{gardenId}/plants/{plantId}/
    public ResponseEntity<?> getPlantById(@PathVariable(value = "gardenId") Long gardenId, @PathVariable(value = "plantId") Long plantId){
        logger.info("Attempt to retrieve a plant with id " + plantId);
        Optional<Plant> plant = plantService.getPlantById(plantId, gardenId);
        if (plant.isPresent()){
            requestResponse.put("message", "Plant retrieved successfully.");
            requestResponse.put("data", plant);
            return new ResponseEntity<>(requestResponse, HttpStatus.OK);
        } else {
            requestResponse.put("message", "Plant not found!");
            logger.severe(requestResponse.get("message").toString());
            return new ResponseEntity<>(requestResponse, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Retrieves the care tracker details for a specific plant.
     *
     * @param plantId The plant id for which to retrieve care tracker records.
     * @return A ResponseEntity containing the list of care tracker records or an error message.
     */
    @GetMapping(path = "/{plantId}/care-tracker/") //http://localhost:9092/gardens/{gardenId}/plants/{plantId}/
    public ResponseEntity<?> getPlantCareTracker(@PathVariable(value = "plantId") Long plantId){
        logger.info("Attempt to retrieve a plant care tracker with plantId " + plantId);
        List<Object[]> careTrackerList = careTrackService.getAllTracksBy(plantId);
        if (!careTrackerList.isEmpty()){
            requestResponse.put("message", "Plant care tracker retrieved successfully.");
            requestResponse.put("data", careTrackerList);
            return new ResponseEntity<>(requestResponse, HttpStatus.OK);
        } else {
            requestResponse.put("message", "Plant care tracker still empty.");
            logger.severe(requestResponse.get("message").toString());
            return new ResponseEntity<>(requestResponse, HttpStatus.NOT_FOUND);
        }
    }

}
