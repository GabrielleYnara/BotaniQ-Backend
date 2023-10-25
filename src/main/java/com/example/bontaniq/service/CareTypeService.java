package com.example.bontaniq.service;

import com.example.bontaniq.repository.CareTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CareTypeService extends ServiceSharedResources{
    private final CareTypeRepository careTypeRepository;
    private final PlantService plantService;

    @Autowired
    public CareTypeService(CareTypeRepository careTypeRepository, PlantService plantService) {
        this.careTypeRepository = careTypeRepository;
        this.plantService = plantService;
    }
}
