package com.example.bontaniq.service;

import com.example.bontaniq.exception.exception.IllegalArgumentException;
import com.example.bontaniq.exception.exception.InformationNotFoundException;
import com.example.bontaniq.model.CareType;
import com.example.bontaniq.model.Plant;
import com.example.bontaniq.repository.CareTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
            Optional<CareType> careTypeOptional = careTypeRepository.findByTypeAndPlantId(careType.getType(), plantId);
            if (careTypeOptional.isPresent()){
                String message = "Care Type " + careType.getType() + " already exist.";
                logger.severe(message);
                return Optional.empty();
            }
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

    public List<CareType> getAllCareTypesByPlantId(Long gardenId, Long plantId){
        logger.info("Initializing retrieve all Care Types for Plant with id " + plantId);
        Optional<Plant> plantOptional = plantService.getPlantById(plantId, gardenId);
        if(plantOptional.isPresent()){
            List<CareType> careTypeList = careTypeRepository.findByPlantId(plantId);
            if (!careTypeList.isEmpty()){
                logger.info("A list of care found." + careTypeList);
                return careTypeList;
            } else {
                String message = "No care type registered for plant " + plantOptional.get().getName();
                logger.severe(message);
                throw new InformationNotFoundException(message);
            }
        } else {
            throw new InformationNotFoundException("Plant not found! Can't keep searching for care needs.");
        }
    }
}
