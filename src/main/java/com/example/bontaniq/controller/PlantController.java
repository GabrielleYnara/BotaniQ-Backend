package com.example.bontaniq.controller;

import com.example.bontaniq.model.Plant;
import com.example.bontaniq.service.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path="/gardens/{gardenId}/plants") // localhost:9092/gardens/{gardenId}/plants
public class PlantController extends SharedResourceContainer{
    private final PlantService plantService;

    @Autowired
    public PlantController(PlantService plantService) {
        this.plantService = plantService;
    }

    @PostMapping(path = "/") //http://localhost:9092/gardens/{gardenId}/plants/
    public ResponseEntity<?> createGarden(@RequestBody Plant plant, @PathVariable(value = "gardenId") Long gardenId){
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
}
