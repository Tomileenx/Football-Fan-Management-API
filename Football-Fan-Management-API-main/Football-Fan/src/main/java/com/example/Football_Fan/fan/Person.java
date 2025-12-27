package com.example.Football_Fan.fan;

import com.example.Football_Fan.club.Club;
import com.example.Football_Fan.fanProfile.FanProfile;
import com.example.Football_Fan.users.AppUser;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class Person {
    @Id
    @GeneratedValue
    private Integer id;

    private String firstname;

    private String lastname;

    @OneToOne(
            mappedBy = "person"
    )
    private FanProfile fanProfile;

    @ManyToOne
    @JoinColumn(
            name = "club_id"
    )
    @JsonBackReference
    private Club club;

    @OneToOne(
            mappedBy = "person"
    )
    private AppUser appUser;

    public Person() {
    }

    public Person(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public FanProfile getFanProfile() {
        return fanProfile;
    }

    public void setFanProfile(FanProfile fanProfile) {
        this.fanProfile = fanProfile;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }
}
