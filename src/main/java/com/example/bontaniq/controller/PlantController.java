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

@Controller
@RequestMapping(path="/gardens/{gardenId}/plants") // localhost:9092/gardens/{gardenId}/plants
public class PlantController extends ControllerSharedResources {
    private final PlantService plantService;
    private final CareTrackService careTrackService;

    @Autowired
    public PlantController(PlantService plantService, CareTrackService careTrackService) {
        this.plantService = plantService;
        this.careTrackService = careTrackService;
    }

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
