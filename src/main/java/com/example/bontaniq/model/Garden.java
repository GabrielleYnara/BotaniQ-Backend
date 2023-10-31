package com.example.bontaniq.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;
/**
 * Represents a garden entity in the database. <br>
 * Each garden has a unique ID, a description, notes, an associated {@link User}, and a list of {@link Plant}s.
 */
@Entity
@Table(name="gardens")
public class Garden {
    /**
     * Unique identifier for the garden.
     * <p>
     * The database generates this ID as a sequence of unused Long values.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    /**
     * A brief description of the garden or its location
     */
    @Column
    private String description;
    /**
     * Additional notes related to the garden.
     */
    @Column
    private String notes;
    /**
     * The user associated with this garden.
     * <p>
     *  Many gardens can belong to a single user.
     * </p>
     */
    @JsonIgnore // to prevent stack overflow
    @ManyToOne
    @JoinColumn(name = "user_id") //to map the relationship
    private User user;
    /**
     * List of plants associated with this garden.
     * <p>
     * If a garden is deleted, all its associated plants are removed as well.
     * </p>
     */
    @OneToMany(mappedBy = "garden", orphanRemoval = true) //maps the relationship
    @LazyCollection(LazyCollectionOption.FALSE) // loads plants list when garden is loaded
    private List<Plant> plantList;

    /**
     * Default constructor for creating an empty Garden object.
     */
    public Garden() {
    }

    /**
     * Constructs a new Garden with a specified description, notes, and associated user.
     *
     * @param description A brief description of the garden.
     * @param notes       Additional notes related to the garden.
     * @param user        The user associated with this garden.
     */
    public Garden(String description, String notes, User user) {
        this.description = description;
        this.notes = notes;
        this.user = user;
    }

    /**
     * Retrieves the garden's unique ID.
     *
     * @return The garden's ID.
     */
    public Long getId() {
        return id;
    }

    /**
     * Updates the garden's unique ID.
     * <p>This does not alter the garden's ID in the database.</p>
     * @param id The new garden's ID
     */
    public void setId(Long id){
        this.id = id;
    }

    /**
     * Retrieves the garden's description.
     *
     * @return The garden's description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Updates the garden's description.
     *
     * @param description The new description of the garden.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retrieves the garden's additional notes.
     * @return The garden's notes.
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Updates the garden's additional notes.
     * @param notes The new notes of the garden.
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * Retrieves the garden's owner.
     * @return The garden's associated user.
     */
    public User getUser() {
        return user;
    }

    /**
     * Updates the garden's owner.
     * @param user The garden's new associated user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Retrieves the list of plants associated with this garden.
     * @return The list of plants.
     */
    public List<Plant> getPlantList() {
        return plantList;
    }

    /**
     * Provides a string representation of the Garden object,
     * including its ID, description, notes, and user.
     *
     * @return A string representation of the Garden.
     */
    @Override
    public String toString() {
        return "Garden{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", notes='" + notes + '\'' +
                ", user=" + user +
                '}';
    }
}
