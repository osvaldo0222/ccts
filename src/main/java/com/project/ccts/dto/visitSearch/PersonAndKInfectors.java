package com.project.ccts.dto.visitSearch;

import com.project.ccts.model.entities.Person;

public class PersonAndKInfectors {
    private Person person;
    private Integer k;

    public PersonAndKInfectors(Person person, Integer k) {
        this.person = person;
        this.k = k;
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
}
