package com.project.ccts.model.entities;

<<<<<<< HEAD
import com.project.ccts.dto.visitSearch.VisitAndTimeShared;
import com.project.ccts.model.entities.Person;

import javax.persistence.*;
import java.util.Collection;
=======
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
>>>>>>> eed74f90337ac5ef17da5396305a9497ed8e2e6a

@Entity
public class PersonAndKInfectors {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Person person;
    private Integer k;
    private Double probabilityOfInfection;
<<<<<<< HEAD
    @ManyToMany
    private Collection<Visit> visits;

=======
    @CreationTimestamp
    private Date date;
>>>>>>> eed74f90337ac5ef17da5396305a9497ed8e2e6a

    public PersonAndKInfectors(){}

    public PersonAndKInfectors(Person person, Integer k, Double probabilityOfInfection) {
        this.person = person;
        this.k = k;
        this.probabilityOfInfection = probabilityOfInfection;
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

<<<<<<< HEAD
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

=======
>>>>>>> eed74f90337ac5ef17da5396305a9497ed8e2e6a
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
