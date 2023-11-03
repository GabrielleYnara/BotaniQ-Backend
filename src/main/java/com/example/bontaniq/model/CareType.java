package com.example.bontaniq.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

/**
 * Represents a care type entity in the database.<br>
 * Each care type has a unique ID, a type, and a frequency of care.
 * <p>
 *     Care types are designed to represent the plant's care needs.
 * </p>
 *
 *  They are associated with a specific {@link Plant} and have a list of
 * associated {@link CareTrack}s, which track the actual care activities.
 */
@Entity
@Table(name = "care_types")
public class CareType {
    /**
     * Unique identifier for the care type.<br>
     * The database generates this ID as a sequence of unused Long values.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //generate sequence of unused values
    @Column
    private Long id;

    /**
     * Type or category of care required for the plant.
     */
    @Column
    private String type;

    /**
     * The frequency with which this care should be provided to the plant.
     */
    @Column
    private String frequency;

    /**
     * The plant that requires this type of care.<br>
     * Many care types can belong to a plant.
     */
    @JsonIgnore // prevents stack overflow
    @ManyToOne
    @JoinColumn(name="plant_id") //maps the relationship
    private Plant plant;

    /**
     * List of care trackers associated with this care type.<br>
     * These trackers represent instances when the care was provided.
     * <p>
     *      If a care type is deleted, all its associated trackers are removed as well.
     * </p>
     */
    @OneToMany(mappedBy = "careType", orphanRemoval = true) // maps the relationship
    @LazyCollection(LazyCollectionOption.FALSE) // loads tracker list when care type is loaded
    private List<CareTrack> careTrackList;

    /**
     * Default constructor for creating an empty CareType object.
     */
    public CareType() {
    }

    /**
     * Constructs a new CareType with the specified type and frequency of care.
     *
     * @param type      Type or category of care.
     * @param frequency The frequency with which care should be provided.
     */
    public CareType(String type, String frequency) {
        this.type = type;
        this.frequency = frequency;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public Plant getPlant() {
        return plant;
    }

    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    public List<CareTrack> getCareTrackList() {
        return careTrackList;
    }

    public void setCareTrackList(List<CareTrack> careTrackList) {
        this.careTrackList = careTrackList;
    }

    /**
     * Provides a string representation of the CareType object, including its id, type, frequency, and associated plant id.
     *
     * @return A string representation of the CareType.
     */
    @Override
    public String toString() {
        return "CareType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", frequency='" + frequency + '\'' +
                ", plant=" + plant.getId() +
                '}';
    }
}
