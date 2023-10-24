package com.example.bontaniq.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="gardens")
public class Garden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //generate sequence of unused Long values
    @Column
    private Long id;
    @Column(unique = true) //persistence on database side
    private String description;
    @Column
    private String notes;
    @JsonIgnore // to prevent stack overflow
    @ManyToOne //many gardens belong to a user
    @JoinColumn(name = "user_id") //to map the relationship
    private User user;

    @OneToMany(mappedBy = "garden", orphanRemoval = true) //maps the relationship and removes plant data of garden is deleted
    @LazyCollection(LazyCollectionOption.FALSE) // loads plants list when garden is loaded
    private List<Plant> plantList;

    public Garden() {
    }

    public Garden(String description, String notes, User user) {
        this.description = description;
        this.notes = notes;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Plant> getPlantList() {
        return plantList;
    }

    public void setPlantList(List<Plant> plantList) {
        this.plantList = plantList;
    }

    @Override
    public String toString() {
        return "Garden{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", notes='" + notes + '\'' +
                ", user=" + user +
                ", plantList=" + plantList.toString() +
                '}';
    }
}
