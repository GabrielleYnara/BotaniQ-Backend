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

@Service
public class GardenService extends ServiceSharedResources{
    private final GardenRepository gardenRepository;

    @Autowired
    public GardenService(GardenRepository gardenRepository) {
        this.gardenRepository = gardenRepository;
    }

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

    public List<Garden> getAllGarden(){
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
