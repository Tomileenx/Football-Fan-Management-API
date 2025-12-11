package com.example.Football_Fan.club;

import com.example.Football_Fan.fan.Person;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Club {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique = true)
    private String name;

    @OneToMany(
            mappedBy = "club"
    )
    @JsonManagedReference
    private List<Person> persons;

    public Club() {
    }

    public Club(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }
}
