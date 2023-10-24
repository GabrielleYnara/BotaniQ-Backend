package com.example.bontaniq.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // generate a sequence of unused Long integer values
    private Long id;
    @Column(unique = true)
    private String emailAddress;
    @Column
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //restricts the access
    private String password;

    @OneToOne(cascade = CascadeType.ALL) //when loading user, load profile as well
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private Profile profile;
    @OneToMany(mappedBy = "user", orphanRemoval = true) // maps the relationship and remove gardenList data if user is deleted
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Garden> gardenList;

    public User() {
    }

    public User(String emailAddress, String password) {
        this.emailAddress = emailAddress;
        this.password = password;
    }

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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", emailAddress='" + emailAddress + '\'' +
                ", password='" + password + '\'' +
                ", profile=" + profile +
                '}';
    }
}
