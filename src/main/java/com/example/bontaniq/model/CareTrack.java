package com.example.bontaniq.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Represents a care tracker entity in the database.<br>
 * Each care tracker has a unique ID, a done status indicating if the care was provided, a date,
 * and is associated with a {@link CareType}.
 * <p>
 *     CareTrack is designed to track the actual care activities provided to a plant.
 * </p>
 */
@Entity
@Table(name="care_tracker")
public class CareTrack {
    /**
     * Unique identifier for the care tracker.<br>
     * The database generates this ID as a sequence of unused Long values.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    /**
     * Indicates if the care activity was done or not.
     */
    @Column
    private boolean done;

    /**
     * The date on which the care activity was done or is scheduled to be done.
     */
    @Column
    private LocalDate date;

    /**
     * The type of care associated with this care tracker.<br>
     * Many care tracker belongs to care type.
     */
    @JsonIgnore // prevents stack overflow
    @ManyToOne
    @JoinColumn(name = "careType_id")
    private CareType careType;

    /**
     * Default constructor for creating an empty CareTrack object.
     */
    public CareTrack() {
    }

    /**
     * Constructs a new CareTrack with the specified done status and date.
     *
     * @param done Indicates if the care activity was done or not.
     * @param date The date for the care activity.
     */
    public CareTrack(boolean done, LocalDate date) {
        this.done = done;
        this.date = date;
    }

    public Long getId() {
        return id;
    }
    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public CareType getCareType() {
        return careType;
    }

    public void setCareType(CareType careType) {
        this.careType = careType;
    }

    /**
     * Provides a string representation of the CareTrack object, including its id, done status, date,
     * and the type of care associated.
     *
     * @return A string representation of the CareTrack.
     */
    @Override
    public String toString() {
        return "CareTracker{" +
                "id=" + id +
                ", done=" + done +
                ", date=" + date +
                ", careType=" + careType.getType() +
                '}';
    }
}
