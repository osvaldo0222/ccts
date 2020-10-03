package com.project.ccts.model.entities;

import com.project.ccts.dto.visitSearch.VisitAndTimeShared;
import com.project.ccts.model.entities.Person;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class PersonAndKInfectors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Person person;
    private Integer k;
    private Double probabilityOfInfection;
    @ManyToMany
    private Collection<Visit> visits;


    public PersonAndKInfectors(){}

    public PersonAndKInfectors(Person person, Integer k) {
        this.person = person;
        this.k = k;
    }

    public Collection<Visit> getVisits() {
        return visits;
    }

    public void setVisits(Collection<Visit> visits) {
        this.visits = visits;
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
    public PersonAndKInfectors(Person person, Integer k, Double probabilityOfInfection,Collection<Visit> visits ) {
        this.person = person;
        this.k = k;
        this.probabilityOfInfection = probabilityOfInfection;
        this.visits = visits;
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
