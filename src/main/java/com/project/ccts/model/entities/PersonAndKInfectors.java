package com.project.ccts.model.entities;

import javax.persistence.*;
import java.util.Collection;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

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

    @CreationTimestamp
    private Date date;

    public PersonAndKInfectors(){}

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Double getProbabilityOfInfection() {
        return probabilityOfInfection;
    }

    public void setProbabilityOfInfection(Double probabilityOfInfection) {
        this.probabilityOfInfection = probabilityOfInfection;
    }

    public Collection<Visit> getVisits() {
        return visits;
    }

    public void setVisits(Collection<Visit> visits) {
        this.visits = visits;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
