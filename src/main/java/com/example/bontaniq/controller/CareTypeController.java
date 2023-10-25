package com.example.bontaniq.controller;

import com.example.bontaniq.service.CareTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/gardens/{gardenId}/plants/{plantId}/cares") // /http://localhost:9092/gardens/{gardenId}/plants/{plantId}/cares
public class CareTypeController extends ControllerSharedResources {
    private final CareTypeService careTypeService;

    @Autowired
    public CareTypeController(CareTypeService careTypeService) {
        this.careTypeService = careTypeService;
    }
}
