package com.example.bontaniq.service;

import com.example.bontaniq.exception.exception.IllegalArgumentException;
import com.example.bontaniq.exception.exception.InformationNotFoundException;
import com.example.bontaniq.model.CareType;
import com.example.bontaniq.model.Plant;
import com.example.bontaniq.repository.CareTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CareTypeService extends ServiceSharedResources{
    private final CareTypeRepository careTypeRepository;
    private final PlantService plantService;

    @Autowired
    public CareTypeService(CareTypeRepository careTypeRepository, PlantService plantService) {
        this.careTypeRepository = careTypeRepository;
        this.plantService = plantService;
    }

    public Optional<CareType> createCareType(Long gardenId, Long plantId, CareType careType){
        logger.info("Initializing create plant care type: ");
        Optional<Plant> plantOptional = plantService.getPlantById(plantId, gardenId);
        if(plantOptional.isPresent()){
            careType.setPlant(plantOptional.get());
            if (careType.getType() != null && careType.getFrequency() != null) {
                logger.info("Care Type created!");
                return Optional.of(careTypeRepository.save(careType));
            } else {
                throw new IllegalArgumentException("All fields must be provided.");
            }
        } else {
            throw new InformationNotFoundException("Plant not found! Can't create a care type without a plant.");
        }
    }
}
