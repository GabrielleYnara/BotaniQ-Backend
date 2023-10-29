package com.example.bontaniq.service;

import com.example.bontaniq.exception.exception.IllegalArgumentException;
import com.example.bontaniq.exception.exception.InformationNotFoundException;
import com.example.bontaniq.model.*;
import com.example.bontaniq.repository.CareTrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CareTrackService extends ServiceSharedResources{
    private final CareTrackRepository careTrackRepository;
    private final PlantService plantService;

    @Autowired
    public CareTrackService(CareTrackRepository careTrackRepository, PlantService plantService) {
        this.careTrackRepository = careTrackRepository;
        this.plantService = plantService;
    }
    public Optional<CareTrack> registerCareTrack(Long gardenId, Long plantId, Long careTypeId, CareTrack careTrack) {
        logger.info("Initializing register plant care: ");
        Optional<Plant> plantOptional = plantService.getPlantById(plantId, gardenId);
        if (plantOptional.isPresent()){
            Optional<CareType> careTypeOptional = plantOptional.get().getCareTypeList().stream()
                    .filter((careType -> Objects.equals(careType.getId(), careTypeId) ))
                    .findFirst();
            if (careTypeOptional.isEmpty()){
                String message = "Care type with id " + careTypeId + " not found";
                logger.severe(message);
                throw new InformationNotFoundException(message);
            }
            careTrack.setCareType(careTypeOptional.get());
            if (careTrack.getDate() == null){
                String message = "Care track needs a date to be saved.";
                logger.severe(message);
                throw new IllegalArgumentException(message);
            }
            logger.info("Care Track saved successfully!");
            return Optional.of(careTrackRepository.save(careTrack));
        } else {
            return Optional.empty();
        }

    } //ToDo: once the caretype get method is implemented, update this.
    //search garden
    //search plant
    //search care type
    //save care track

    public List<Object[]> getAllTracksBy(Long plantId){
        logger.info("Initializing retrieve all gardens:");
        List<Object[]> plantCareTracker = careTrackRepository.findCareTrackDetailsByPlantId(plantId);
        if (!plantCareTracker.isEmpty()){
            logger.info("Plant care tracker found.");
            return plantCareTracker;
        } else {
            String message = "Plant care tracker empty.";
            logger.severe(message);
            throw new InformationNotFoundException(message);
        }
    }
}
