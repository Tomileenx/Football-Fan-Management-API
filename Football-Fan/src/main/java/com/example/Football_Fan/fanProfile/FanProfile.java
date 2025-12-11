package com.example.Football_Fan.fanProfile;

import com.example.Football_Fan.fan.Person;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class FanProfile {
    @Id
    @GeneratedValue
    private Integer id;

    private int age;

    private String nationality;

    private LocalDate dateJoined;

    @OneToOne
    @JoinColumn(
            name = "person_id"
    )
    private Person person;

    public FanProfile() {
    }

    public FanProfile(int age, String nationality, LocalDate dateJoined) {
        this.age = age;
        this.nationality = nationality;
        this.dateJoined = dateJoined;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public LocalDate getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(LocalDate dateJoined) {
        this.dateJoined = dateJoined;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
