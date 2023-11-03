package com.example.bontaniq.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

/**
 * Represents a plant entity in the database.<br>
 * Each plant has a unique ID, a name, a type, an associated {@link Garden}, and a list of {@link CareType}s.
 */
@Entity
@Table(name="plants")
public class Plant {
    /**
     * Unique identifier for the plant.<br>
     * The database generates this ID as a sequence of unused Long values.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    /**
     * Name of the plant.
     */
    @Column
    private String name;

    /**
     * Type or category of the plant.
     */
    @Column
    private String type;

    /**
     * The garden where this plant is located or belongs to.<br>
     * Many plants can belong to a garden.
     */
    @JsonIgnore // to prevent stack overflow
    @ManyToOne
    @JoinColumn(name = "garden_id") // to map the relationship
    private Garden garden;

    /**
     * List of care types associated with this plant.<br>
     * If a plant is deleted, all its associated care types are removed as well.
     */
    @OneToMany(mappedBy = "plant", orphanRemoval = true) // one plane can have many care types
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<CareType> careTypeList;

    /**
     * Default constructor for creating an empty Plant object.
     */
    public Plant() {
    }

    /**
     * Constructs a new Plant with the specified name, type, and associated garden.
     *
     * @param name   Plant's name.
     * @param type   Plant's type or category.
     * @param garden The garden where this plant is located or belongs to.
     */
    public Plant(String name, String type, Garden garden) {
        this.name = name;
        this.type = type;
        this.garden = garden;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Garden getGarden() {
        return garden;
    }

    public void setGarden(Garden garden) {
        this.garden = garden;
    }

    public List<CareType> getCareTypeList() {
        return careTypeList;
    }

    public void setCareTypeList(List<CareType> careTypeList) {
        this.careTypeList = careTypeList;
    }

    /**
     * Provides a string representation of the Plant object, including its id, name, type, and associated garden's name.
     *
     * @return A string representation of the Plant.
     */
    @Override
    public String toString() {
        return "Plant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", garden=" + garden.getDescription() +
                '}';
    }
}
