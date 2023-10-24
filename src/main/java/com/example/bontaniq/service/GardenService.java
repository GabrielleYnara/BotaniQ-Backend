package com.example.bontaniq.service;

import com.example.bontaniq.exception.exception.InformationExistException;
import com.example.bontaniq.exception.exception.InformationNotFoundException;
import com.example.bontaniq.model.Garden;
import com.example.bontaniq.model.User;
import com.example.bontaniq.repository.GardenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class GardenService {
    private final GardenRepository gardenRepository;
    private final UserService userService;

    private Logger logger = Logger.getLogger(GardenRepository.class.getName());

    @Autowired
    public GardenService(GardenRepository gardenRepository, UserService userService) {
        this.gardenRepository = gardenRepository;
        this.userService = userService;
    }

    public Optional<Garden> createGarden(Garden garden){
        logger.info("Initializing garden creation:");
        User currentUser = userService.getCurrentLoggedInUser();
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
    public Optional<Garden> getGarden(Long gardenId){
        logger.info("Initializing retrieve garden:");
        User currentUser = userService.getCurrentLoggedInUser();
        Optional<Garden> gardenOptional = gardenRepository.findByIdAndUserId(gardenId, currentUser.getId());
        if (gardenOptional.isPresent()){
            logger.info("Garden " + gardenOptional.get().getDescription() + " found.");
            return gardenOptional;
        } else {
            logger.severe("Garden with id " + gardenId + " not found.");
            throw new InformationNotFoundException("Garden with id " + gardenId + " not found.");
        }
    }
}
