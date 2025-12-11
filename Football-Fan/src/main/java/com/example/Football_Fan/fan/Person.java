package com.example.Football_Fan.fan;

import com.example.Football_Fan.club.Club;
import com.example.Football_Fan.fanProfile.FanProfile;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class Person {
    @Id
    @GeneratedValue
    private Integer id;

    private String firstname;

    private String lastname;

    @Column(unique = true)
    private String email;

    private int age;

    @OneToOne(
            mappedBy = "person",
            cascade = CascadeType.ALL
    )
    private FanProfile fanProfile;

    @ManyToOne
    @JoinColumn(
            name = "club-name"
    )
    @JsonBackReference
    private Club club;

    public Person() {
    }

    public Person(String firstname, String lastname, String email, int age) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public int getAge() {
//        return age;
//    }
//
//    public void setAge(int age) {
//        this.age = age;
//    }

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
}
