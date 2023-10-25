package com.example.bontaniq.controller;

import com.example.bontaniq.model.CareType;
import com.example.bontaniq.service.CareTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping(path = "/gardens/{gardenId}/plants/{plantId}/cares") // /http://localhost:9092/gardens/{gardenId}/plants/{plantId}/cares
public class CareTypeController extends ControllerSharedResources {
    private final CareTypeService careTypeService;

    @Autowired
    public CareTypeController(CareTypeService careTypeService) {
        this.careTypeService = careTypeService;
    }

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
