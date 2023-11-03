package com.example.bontaniq.service;

import com.example.bontaniq.exception.IllegalArgumentException;
import com.example.bontaniq.exception.InformationNotFoundException;
import com.example.bontaniq.model.Garden;
import com.example.bontaniq.model.Plant;
import com.example.bontaniq.repository.PlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents the Plant Service, responsible for housing business logic related to plants.<br>
 *<p>
 *     This class serves as an intermediary between the controller and the repository,
 *     invoking the repository to perform CRUD operations on users.
 *</p>
 */
@Service
public class PlantService extends ServiceSharedResources{
    private final PlantRepository plantRepository;
    private final GardenService gardenService;

    /**
     * Injects dependencies and enables plantService to access the resources.
     *
     * @param plantRepository The repository for plant-related CRUD operations.
     * @param gardenService The service for garden-related business logic operations.
     */

    @Autowired
    public PlantService(PlantRepository plantRepository, GardenService gardenService) {
        this.plantRepository = plantRepository;
        this.gardenService = gardenService;
    }

    /**
     * Creates a new plant associated with the currently logged-in user and the gardenId provided.
     *
     * @param plant The plant details to be created.
     * @param gardenId The garden id, which this plant belongs to.
     * @return An Optional with the recently created plant;
     * @throws IllegalArgumentException If the 'name' or 'type' fields of the plant are null or empty.
     * @throws InformationNotFoundException If garden not found.
     */
    public Optional<Plant> createPlant(Plant plant, Long gardenId){
        logger.info("Initializing create plant: ");
        Optional<Garden> gardenOptional = gardenService.getGardenById(gardenId);
        if(gardenOptional.isPresent()){
            plant.setGarden(gardenOptional.get());
            if (plant.getName() != null && plant.getType() != null) {
                logger.info("Plant created!");
                return Optional.of(plantRepository.save(plant));
            } else {
                throw new IllegalArgumentException("The 'name' and 'type' fields of the plant must not be null or empty.");
            }
        } else {
            throw new InformationNotFoundException("Garden not found! Can't create a plant without a garden.");
        }
    }

    /**
     * Retrieves a plant associated with currently logged-in user by the given garden, and plant id.
     * @param plantId The plant id.
     * @param gardenId The garden id.
     * @return An Optional with the Plant object if successful retrieval.
     * @throws InformationNotFoundException If the garden with the given 'gardenId' is not found, or if a plant with the given 'plantId' is not found within the located garden.
     */
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
                String message = "Plant not found in Garden :" + garden.get().getDescription();
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
