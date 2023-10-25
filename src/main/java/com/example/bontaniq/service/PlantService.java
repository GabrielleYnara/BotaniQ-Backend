package com.example.bontaniq.service;

import com.example.bontaniq.exception.exception.IllegalArgumentException;
import com.example.bontaniq.exception.exception.InformationNotFoundException;
import com.example.bontaniq.model.Garden;
import com.example.bontaniq.model.Plant;
import com.example.bontaniq.repository.PlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PlantService extends ServiceSharedResources{
    private final PlantRepository plantRepository;
    private final GardenService gardenService;


    @Autowired
    public PlantService(PlantRepository plantRepository, GardenService gardenService) {
        this.plantRepository = plantRepository;
        this.gardenService = gardenService;
    }

    public Optional<Plant> createPlant(Plant plant, Long gardenId){
        logger.info("Initializing create plant: ");
        Optional<Garden> gardenOptional = gardenService.getGardenById(gardenId);
        if(gardenOptional.isPresent()){
            plant.setGarden(gardenOptional.get());
            if (plant.getName() != null && plant.getType() != null) {
                logger.info("Plant created!");
                return Optional.of(plantRepository.save(plant));
            } else {
                throw new IllegalArgumentException("All fields must be provided.");
            }
        } else {
            throw new InformationNotFoundException("Garden not found! Can't create a plant without a garden.");
        }
    }

    public Optional<Plant> getPlantById(Long plantId, Long gardenId){
        logger.info("Initializing retrieve plant by id.");
        Optional<Garden> garden = gardenService.getGardenById(gardenId);

        if (garden.isPresent()){
            List<Plant> plantList = garden.get().getPlantList();
            Optional<Plant> plantFound = plantList.stream().filter((plant) -> Objects.equals(plant.getId(), plantId)).findFirst();
            if (plantFound.isPresent()) {
                logger.info("Found " + plantFound.get().getName());
                return plantFound;
            } else {
                String message = "Plant not found.";
                logger.severe(message);
                throw new InformationNotFoundException(message);
            }
        } else {
            String message = "Garden not found, can't keep searching for plant.";
            logger.severe(message);
            throw new InformationNotFoundException(message);
        }
    }

}
