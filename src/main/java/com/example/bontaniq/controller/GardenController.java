package com.example.bontaniq.controller;

import com.example.bontaniq.model.Garden;
import com.example.bontaniq.service.GardenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping(path="/gardens") //http://localhost:9092/gardens
public class GardenController extends SharedResourceContainer {
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
//            logger.severe("Garden creation failed!");
            requestResponse.put("message", "Garden creation failed!");
            logger.severe(requestResponse.get("message").toString());
            return new ResponseEntity<>(requestResponse, HttpStatus.CONFLICT);
        }
    }
}
