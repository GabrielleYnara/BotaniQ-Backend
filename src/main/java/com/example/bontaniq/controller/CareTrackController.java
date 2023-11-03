package com.example.bontaniq.controller;

import com.example.bontaniq.model.CareTrack;
import com.example.bontaniq.service.CareTrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
/**
 * Handles HTTP requests for tracking the care activities for plants within a garden.
 */
@RestController
@RequestMapping(path = "/gardens/{gardenId}/plants/{plantId}/cares/{careId}/care-tracker") // localhost:9092/gardens/{gardenId}/plants/{plantId}/cares/{careId}/care-tracker/
public class CareTrackController extends ControllerSharedResources{
    private final CareTrackService careTrackService;

    /**
     * Injects dependencies and enables careTrackController to access the resources.
     *
     * @param careTrackService The service for care track operations.
     */
    @Autowired
    public CareTrackController(CareTrackService careTrackService) {
        this.careTrackService = careTrackService;
    }

    /**
     * Registers a new care activity for a specific care type associated with a plant in a garden.
     *
     * @param gardenId  The garden id where the plant resides.
     * @param plantId   The plant id for which care is being tracked.
     * @param careTypeId The care type id associated with this care track.
     * @param careTrack The CareTrack object containing care details.
     * @return A ResponseEntity containing the registered care track or an error message.
     */
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
