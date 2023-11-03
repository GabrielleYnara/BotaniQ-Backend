package com.example.bontaniq.service;

import com.example.bontaniq.exception.IllegalArgumentException;
import com.example.bontaniq.exception.InformationExistException;
import com.example.bontaniq.exception.InformationNotFoundException;
import com.example.bontaniq.model.CareType;
import com.example.bontaniq.model.Plant;
import com.example.bontaniq.repository.CareTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Represents the Care Type Service, responsible for housing business logic related to care types.<br>
 *<p>
 *     This class serves as an intermediary between the controller and the repository,
 *     invoking the repository to perform CRUD operations on care types.
 *</p>
 */
@Service
public class CareTypeService extends ServiceSharedResources{
    private final CareTypeRepository careTypeRepository;
    private final PlantService plantService;

    /**
     * Injects dependencies and enables careTypeService to access the resources.
     * @param careTypeRepository The repository for careType-related CRUD operations.
     * @param plantService The service plant business logic operations.
     */
    @Autowired
    public CareTypeService(CareTypeRepository careTypeRepository, PlantService plantService) {
        this.careTypeRepository = careTypeRepository;
        this.plantService = plantService;
    }

    /**
     * Creates a new care type associated with the currently logged-in user and the provided gardenId and plantId.
     * @param gardenId The garden id.
     * @param plantId  The plant id.
     * @param careType The plant care type details to be created.
     * @return An Optional with the recently created plant;
     * @throws InformationExistException If the care type with the same 'type' and associated with the given 'plantId' already exists.
     * @throws IllegalArgumentException If the care 'type' or 'frequency' are null or empty.
     * @throws InformationNotFoundException If a plant with the given 'plantId' is not found.
     */
    public Optional<CareType> createCareType(Long gardenId, Long plantId, CareType careType){
        logger.info("Initializing create plant care type: ");
        Optional<Plant> plantOptional = plantService.getPlantById(plantId, gardenId);
        if(plantOptional.isPresent()){
            Optional<CareType> careTypeOptional = careTypeRepository.findByTypeAndPlantId(careType.getType(), plantId);
            if (careTypeOptional.isPresent()){
                String message = "Care Type " + careType.getType() + " already exist.";
                logger.severe(message);
                throw new InformationExistException(message);
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

    /**
     * Retrieves all care type associated with given garden id, and plant id.
     *
     * @param gardenId The garden id where the plant is located.
     * @param plantId The plant id for which to retrieve care types.
     * @return A list of CareType objects associated with the plant.
     * @throws InformationNotFoundException  If no care types are registered for the plant or if the plant with the given 'plantId' is not found.
     */
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
