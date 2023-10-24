package com.example.bontaniq.controller;

import com.example.bontaniq.service.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path="/gardens/{gardenId}/plants") // localhost:9092//gardens/{gardenId}/plants
public class PlantController extends SharedResourceContainer{
    private final PlantService plantService;

    @Autowired
    public PlantController(PlantService plantService) {
        this.plantService = plantService;
    }
}
