package com.example.bontaniq.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

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

    @Override
    public String toString() {
        return "CareType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", frequency='" + frequency + '\'' +
                ", plant=" + plant +
                '}';
    }
}
