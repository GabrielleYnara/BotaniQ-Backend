package com.example.bontaniq.controller;

import com.example.bontaniq.model.Garden;
import com.example.bontaniq.service.GardenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="/gardens") //http://localhost:9092/gardens
public class GardenController extends ControllerSharedResources {
    private final GardenService gardenService;

    @Autowired
    public GardenController(GardenService gardenService) {
        this.gardenService = gardenService;
    }

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

    @GetMapping(path = "/") //http://localhost:9092/gardens/
    public ResponseEntity<?> getAllGardens(){
        logger.info("Attempt to retrieve a garden list.");
        List<Garden> gardenList = gardenService.getAllGarden();
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
