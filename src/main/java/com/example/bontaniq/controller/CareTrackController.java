package com.example.bontaniq.controller;

import com.example.bontaniq.model.CareTrack;
import com.example.bontaniq.service.CareTrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/gardens/{gardenId}/plants/{plantId}/cares/{careId}/care-tracker") // localhost:9092/gardens/{gardenId}/plants/{plantId}/cares/{careId}/care-tracker/
public class CareTrackController extends ControllerSharedResources{
    private final CareTrackService careTrackService;

    @Autowired
    public CareTrackController(CareTrackService careTrackService) {
        this.careTrackService = careTrackService;
    }

    @PostMapping(path = "/") //http://localhost:9092/gardens/{gardenId}/plants/{plantId}/cares/{careId}/care-tracker/
    public ResponseEntity<?> registerCareTrack(@PathVariable(value = "gardenId") Long gardenId, @PathVariable(value = "plantId") Long plantId, @PathVariable(value = "careId") Long careTypeId, @RequestBody CareTrack careTrack){
        logger.info("Attempt to create a new Care Track.");
        Optional<CareTrack> newCareTrack = careTrackService.registerCareTrack(gardenId, plantId, careTypeId, careTrack);
        if (newCareTrack.isPresent()){
            requestResponse.put("message", "Plant care registered successfully.");
            requestResponse.put("data", newCareTrack);
            return new ResponseEntity<>(requestResponse, HttpStatus.CREATED);
        } else {
            requestResponse.put("message", "Plant care registration failed!");
            logger.severe(requestResponse.get("message").toString());
            return new ResponseEntity<>(requestResponse, HttpStatus.CONFLICT);
        }
    }

}
