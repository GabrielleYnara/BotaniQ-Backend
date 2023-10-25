package com.example.bontaniq.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
@Entity
@Table(name="care_tracker")
public class CareTracker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private boolean done;
    @Column
    private LocalDate date;

    @JsonIgnore // prevents stack overflow
    @ManyToOne // many care tracker belongs to care type
    @JoinColumn(name = "careType_id")
    private CareType careType;

    public CareTracker() {
    }

    public CareTracker(boolean done, LocalDate date) {
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

    @Override
    public String toString() {
        return "CareTracker{" +
                "id=" + id +
                ", done=" + done +
                ", date=" + date +
                ", careType=" + careType +
                '}';
    }
}
