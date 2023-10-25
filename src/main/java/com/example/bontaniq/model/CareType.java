package com.example.bontaniq.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "care_types")
public class CareType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //generate sequence of unused values
    @Column
    private Long id;
    @Column(unique = true) //database persistence
    private String type;
    @Column
    private String frequency;
    @JsonIgnore // prevents stack overflow
    @ManyToOne //many care types belong to a plant
    @JoinColumn(name="plant_id")
    private Plant plant;

    @OneToMany(mappedBy = "careType", orphanRemoval = true) // one care type has many care trackers, removes care trackers if care type is deleted
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<CareTrack> careTrackList;

    public CareType() {
    }

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

    @Override
    public String toString() {
        return "CareType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", frequency='" + frequency + '\'' +
                ", plant=" + plant +
                ", careTrackList=" + careTrackList +
                '}';
    }
}
