package com.example.bontaniq.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

/**
 * Represents a user entity in the database.<br>
 * Each user has a unique ID, a unique email, a password, an associated {@link Profile}, and a list of {@link Garden}s.
 */
@Entity
@Table(name="users")
public class User {
    /**
     * Unique identifier for the user.<br>
     * The database generates this ID as a sequence of unused Long values.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * A unique email address.
     */
    @Column(unique = true)
    private String emailAddress;

    /**
     * A password with restricted visibility when the User object is converted to JSON.
     */
    @Column
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    /**
     * A profile associated with this user.<br>
     * A user can have only one profile.
     */
    @OneToOne(cascade = CascadeType.ALL) //when loading user, load profile as well
    @JoinColumn(name = "profile_id", referencedColumnName = "id") //maps the relationship
    private Profile profile;

    /**
     * List of gardens associated with this user.
     * <p>
     *   If a user is deleted, all its associated gardens are removed as well.
     * </p>
     */
    @OneToMany(mappedBy = "user", orphanRemoval = true) // maps the relationship
    @LazyCollection(LazyCollectionOption.FALSE) //when loading user, loads garden list as well
    private List<Garden> gardenList;

    /**
     * Default constructor for creating an empty User object.
     */
    public User() {
    }

    /**
     * Constructs a new User with specified email and password.
     * @param emailAddress The user's email.
     * @param password The user's password.
     */
    public User(String emailAddress, String password) {
        this.emailAddress = emailAddress;
        this.password = password;
    }

    /**
     * Constructs a new User with the specified ID, email, password, and associated profile.
     * @param id The user's unique id.
     * @param emailAddress The user's email.
     * @param password The user's password.
     * @param profile The user's associated profile.
     */
    public User(Long id, String emailAddress, String password, Profile profile) {
        this.id = id;
        this.emailAddress = emailAddress;
        this.password = password;
        this.profile = profile;
    }

    public Long getId() {
        return id;
    }


    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    /**
     * Provides a string representation of the User object, including its id, email, and profile.
     * @return A string representation of the User.
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", emailAddress='" + emailAddress + '\'' +
                ", profile=" + profile +
                '}';
    }
}
