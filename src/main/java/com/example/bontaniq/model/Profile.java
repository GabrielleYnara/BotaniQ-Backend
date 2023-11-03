package com.example.bontaniq.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Represents a profile entity in the database.<br>
 * Each profile has a unique ID, an associated {@link User}, and their first name, last name, and bio.
 */
@Entity
@Table(name = "profiles")
public class Profile {
    /**
     * Unique identifier for the profile.<br>
     * The database generates this ID as a sequence of unused Long values.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    /**
     * The user's first name.
     */
    @Column
    private String firstName;

    /**
     * The user's last name.
     */
    @Column
    private String lastName;

    /**
     * A brief description or biography of the user.
     */
    @Column
    private String bio;

    /**
     * The user associated with this profile.<br>
     * Each profile is associated with exactly one user.
     */
    @JsonIgnore //prevents stack overflow
    @OneToOne(mappedBy = "profile") // maps the relationship between user and profile
    private User user;

    /**
     * Default constructor for creating an empty User object.
     */
    public Profile() {
    }

    /**
     * Constructs a new Profile with the specified user's first name and last name.
     *
     * @param firstName User's first name.
     * @param lastName  User's last name.
     */
    public Profile(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Constructs a new Profile with all details specified.
     *
     * @param id        The profile's unique id.
     * @param firstName User's first name.
     * @param lastName  User's last name.
     * @param bio       A brief description or biography about the user.
     * @param user      The associated user for this profile.
     */
    public Profile(Long id, String firstName, String lastName, String bio, User user) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = bio;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Provides a string representation of the Profile object, including its id, user's first name, last name, and bio.<br>
     *
     * @return A string representation of the Profile.
     */
    @Override
    public String toString() {
        return "Profile{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", bio='" + bio + '\'' +
                ", user=" + user.getId() +
                '}';
    }
}
