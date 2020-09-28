package com.project.ccts.model.entities;

import com.project.ccts.model.entities.Person;

import javax.persistence.*;

@Entity
public class PersonAndKInfectors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Person person;
    private Integer k;
    private Double probabilityOfInfection;

    public PersonAndKInfectors(){}

    public PersonAndKInfectors(Person person, Integer k) {
        this.person = person;
        this.k = k;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PersonAndKInfectors(Person person, Integer k, Double probabilityOfInfection) {
        this.person = person;
        this.k = k;
        this.probabilityOfInfection = probabilityOfInfection;
    }

    public Double getProbabilityOfInfection() {
        return probabilityOfInfection;
    }

    public void setProbabilityOfInfection(Double probabilityOfInfection) {
        this.probabilityOfInfection = probabilityOfInfection;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Integer getK() {
        return k;
    }

    public void setK(Integer k) {
        this.k = k;
    }

    @Override
    public String toString() {
        return "PersonAndKInfectors{" +
                "person=" + person.getId() +
                ", k=" + k +
                ", probabilityOfInfection=" + probabilityOfInfection +
                '}';
    }
}
