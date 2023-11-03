package com.example.bontaniq.service;

import com.example.bontaniq.exception.IllegalArgumentException;
import com.example.bontaniq.exception.InformationNotFoundException;
import com.example.bontaniq.model.*;
import com.example.bontaniq.repository.CareTrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Represents the Care Track Service, responsible for housing business logic related to care tracks.<br>
 *<p>
 *     This class serves as an intermediary between the controller and the repository,
 *     invoking the repository to perform CRUD operations on care tracks.
 *</p>
 */
@Service
public class CareTrackService extends ServiceSharedResources{
    private final CareTrackRepository careTrackRepository;
    private final PlantService plantService;

    /**
     * Injects dependencies and enables careTypeService to access the resources.
     *
     * @param careTrackRepository The repository for careTrack-related CRUD operations.
     * @param plantService The service plant business logic operations.
     */
    @Autowired
    public CareTrackService(CareTrackRepository careTrackRepository, PlantService plantService) {
        this.careTrackRepository = careTrackRepository;
        this.plantService = plantService;
    }

    /**
     * Registers a new care track for a specific care type associated with a plant and garden.
     *
     * @param gardenId The garden id where the plant is located.
     * @param plantId The plant id for which the care is being tracked.
     * @param careTypeId The care type id that the care track is recording.
     * @param careTrack The CareTrack object with details to be saved.
     * @return An Optional with the saved CareTrack object if successful; otherwise, an empty Optional.
     * @throws InformationNotFoundException If the plant or care type is not found.
     * @throws IllegalArgumentException If the care track date is null.
     */
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

    }

    /**
     * Retrieves all care track records for a given plant.
     *
     * @param plantId The plant id for which to retrieve care tracks.
     * @return A list of care track records for the specified plant.
     * @throws InformationNotFoundException If no care track records are found for the plant.
     */
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
