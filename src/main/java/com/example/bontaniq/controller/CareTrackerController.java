package com.example.bontaniq.controller;

import com.example.bontaniq.service.CareTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/gardens/{gardenId}/plants/{plantId}/cares/{careId}/care-tracker") // localhost:9092/gardens/{gardenId}/plants/{plantId}/cares/{careId}/care-tracker/
public class CareTrackerController extends ControllerSharedResources{
    private final CareTrackerService careTrackerService;

    @Autowired
    public CareTrackerController(CareTrackerService careTrackerService) {
        this.careTrackerService = careTrackerService;
    }

}
