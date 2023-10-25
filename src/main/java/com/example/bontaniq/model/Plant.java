package com.example.bontaniq.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="plants")
public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String name;
    @Column
    private String type;
    @JsonIgnore // to prevent stack overflow
    @ManyToOne // many plants belongs to a garden
    @JoinColumn(name = "garden_id") // to map the relationship
    private Garden garden;
    @OneToMany(mappedBy = "plant") // one plane can have many care types
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<CareType> careTypeList;

    public Plant() {
    }

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

    @Override
    public String toString() {
        return "Plant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", garden=" + garden +
                '}';
    }
}
