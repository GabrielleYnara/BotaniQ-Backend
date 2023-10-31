package com.example.bontaniq.service;

import com.example.bontaniq.exception.InformationExistException;
import com.example.bontaniq.exception.InformationNotFoundException;
import com.example.bontaniq.model.Garden;
import com.example.bontaniq.model.User;
import com.example.bontaniq.repository.GardenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

/**
 * Represents the Garden Service, responsible for housing business logic related to gardens.<br>
 *<p>
 *     This class serves as an intermediary between the controller and the repository,
 *     invoking the repository to perform CRUD operations on gardens.
 *</p>
 */
@Service
public class GardenService extends ServiceSharedResources{
    private final GardenRepository gardenRepository;

    /**
     * Injects dependencies and enables gardenService to access the resources.
     * @param gardenRepository The repository for garden-related CRUD operations.
     */
    @Autowired
    public GardenService(GardenRepository gardenRepository) {
        this.gardenRepository = gardenRepository;
    }

    /**
     * Creates a new garden associated with the currently logged-in user.
     * <p>
     * Duplicated garden descriptions are not accepted.
     * </p>
     *
     * @param garden The garden details to be created.
     * @return An Optional containing the created Garden object if successful;<br>
     *         an empty Optional if a garden with the provided description already exists for the user.
     * @throws InformationExistException if a garden with the provided description already exists for the user.
     */
    public Optional<Garden> createGarden(Garden garden){
        logger.info("Initializing garden creation:");
        User currentUser = getCurrentLoggedInUser();
        // check if a garden with description already exists
        Optional<Garden> gardenOptional = gardenRepository.findByDescriptionAndUserId(garden.getDescription(), currentUser.getId());
        // if it does, returns error
        if (gardenOptional.isPresent()){
            throw new InformationExistException("Garden with description '" + garden.getDescription() + "' already exists.");
        }
        else {
            garden.setUser(currentUser);
            logger.info("Garden " + garden.getDescription() + " created!");
            return Optional.of(gardenRepository.save(garden));
        }
    }

    /**
     * Retrieves a garden associated with currently logged-in user by the given garden id.
     * @param gardenId The garden id.
     * @return An Optional containing the Garden object if successful retrieval.
     * @throws InformationNotFoundException If the Garden not found.
     */
    public Optional<Garden> getGardenById(Long gardenId){
        logger.info("Initializing retrieve garden:");
        User currentUser = getCurrentLoggedInUser();
        Optional<Garden> gardenOptional = gardenRepository.findByIdAndUserId(gardenId, currentUser.getId());
        if (gardenOptional.isPresent()){
            logger.info("Garden " + gardenOptional.get().getDescription() + " found.");
            return gardenOptional;
        } else {
            logger.severe("Garden with id " + gardenId + " not found.");
            throw new InformationNotFoundException("Garden with id " + gardenId + " not found.");
        }
    }

    /**
     * Retrieves all the gardens associated with currently logged-in user.
     * @return A list of gardens if successful retrieval.
     * @throws InformationNotFoundException If no garden was found.
     */
    public List<Garden> getAllGardens(){
        logger.info("Initializing retrieve all gardens:");
        User currentUser = getCurrentLoggedInUser();
        List<Garden> gardenList = gardenRepository.findAllByUserId(currentUser.getId());
        if (!gardenList.isEmpty()){
            logger.info("Garden list found.");
            return gardenList;
        } else {
            String message = "Garden list empty.";
            logger.severe(message);
            throw new InformationNotFoundException(message);
        }
    }

    /**
     * Updates the garden associated with the currently logged-in user by the provided garden ID.
     * <p>
     * This method uses reflection to loop through each field of the Garden class. For each field, it checks
     * if there's a new value in the provided garden object that's different from the original garden's value.
     * If so, it updates the field in the original garden with the new value.
     * </p>
     *
     * @param garden The garden object containing the updated details. <br>
     *               Only fields with non-null values will be considered for updates.
     * @param gardenId The ID of the garden to be updated.
     * @return An Optional containing the updated Garden object if the update was successful.
     * @throws InformationNotFoundException If no garden with the given ID is found for the currently logged-in user.
     * @throws IllegalAccessException If there's an error accessing the fields of the Garden class using reflection.
     */
    public Optional<Garden> updateGarden(Garden garden, Long gardenId) throws IllegalAccessException {
        logger.info("Initializing garden update");
        User user = getCurrentLoggedInUser();
        Optional<Garden> originalGarden = gardenRepository.findByIdAndUserId(gardenId, user.getId());
        if (originalGarden.isPresent()){
            logger.info("Garden record found.");
            try {
                for (Field field : Garden.class.getDeclaredFields()) { //loop through class fields
                    field.setAccessible(true); //make private fields accessible
                    Object newValue = field.get(garden);
                    Object originalValue = field.get(originalGarden.get());
                    if (newValue != null && !newValue.equals(originalValue)) { //if not null and different from original
                        field.set(originalGarden.get(), newValue);
                    }
                }
                logger.info("Garden updated!");
                return Optional.of(gardenRepository.save(originalGarden.get()));
            } catch (IllegalArgumentException e){
                throw new IllegalAccessException(e.getMessage());
            }
        } else {
            throw new InformationNotFoundException("Garden with id " + gardenId + "not found.");
        }
    }
}
