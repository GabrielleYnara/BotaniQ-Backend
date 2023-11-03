package com.example.bontaniq.controller;

import com.example.bontaniq.model.CareType;
import com.example.bontaniq.service.CareTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Handles HTTP requests related to care types for plants in a garden.
 */
@Controller
@RequestMapping(path = "/gardens/{gardenId}/plants/{plantId}/cares") // /http://localhost:9092/gardens/{gardenId}/plants/{plantId}/cares
public class CareTypeController extends ControllerSharedResources {
    private final CareTypeService careTypeService;

    /**
     * Injects dependencies and enables careTypeController to access the resources.
     * @param careTypeService The Service responsible for care type business logic operations.
     */
    @Autowired
    public CareTypeController(CareTypeService careTypeService) {
        this.careTypeService = careTypeService;
    }

    /**
     * Retrieves all care types associated with a specific plant in a garden.
     *
     * @param gardenId The garden id.
     * @param plantId The plant id.
     * @return A ResponseEntity containing the list of care types or an error message.
     */
    @GetMapping (path = "/") //http://localhost:9092/gardens/{gardenId}/plants/{plantId}/cares/
    public ResponseEntity<?> getAllCareTypesByPlantId(@PathVariable(value = "gardenId") Long gardenId, @PathVariable(value = "plantId") Long plantId) {
        logger.info("Attempt to retrieve all Care Type for a plant.");
        List<CareType> careTypeList = careTypeService.getAllCareTypesByPlantId(gardenId, plantId);
        if (!careTypeList.isEmpty()){
            requestResponse.put("message", "List of care types retrieved successfully.");
            requestResponse.put("data", careTypeList);
            return new ResponseEntity<>(requestResponse, HttpStatus.OK);
        } else {
            requestResponse.put("message", "No care types found for the specified plant.");
            logger.severe(requestResponse.get("message").toString());
            return new ResponseEntity<>(requestResponse, HttpStatus.CONFLICT);
        }
    }

    /**
     * Creates a new care type and associates it with a plant in a garden.
     *
     * @param careType The CareType object to be created.
     * @param gardenId The garden id where the plant is located.
     * @param plantId The plant id to which the care type will be associated.
     * @return A ResponseEntity containing the created care type or an error message.
     */
    @PostMapping(path = "/") //http://localhost:9092/gardens/{gardenId}/plants/{plantId}/cares/
    public ResponseEntity<?> createCareType(@RequestBody CareType careType, @PathVariable(value = "gardenId") Long gardenId, @PathVariable(value = "plantId") Long plantId){
        logger.info("Attempt to create a new Care Type.");
        Optional<CareType> newCareType = careTypeService.createCareType(gardenId, plantId, careType);
        if (newCareType.isPresent()){
            requestResponse.put("message", "Plant care type saved successfully.");
            requestResponse.put("data", newCareType);
            return new ResponseEntity<>(requestResponse, HttpStatus.CREATED);
        } else {
            requestResponse.put("message", "Plant care type creation failed!");
            logger.severe(requestResponse.get("message").toString());
            return new ResponseEntity<>(requestResponse, HttpStatus.CONFLICT);
        }
    }
}
