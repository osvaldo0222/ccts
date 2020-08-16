package com.project.ccts.model.entities;


import com.project.ccts.model.entities.SupplementaryDataStatistics.NewCase;
import com.project.ccts.model.entities.SupplementaryDataStatistics.NewDeath;
import com.project.ccts.model.entities.SupplementaryDataStatistics.NewTests;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class GlobalStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String country;
    @OneToOne(cascade = CascadeType.ALL)
    private NewCase cases;
    @OneToOne(cascade = CascadeType.ALL)
    private NewDeath deaths;
    @OneToOne(cascade = CascadeType.ALL)
    private NewTests tests;
    private LocalDate date;

    public GlobalStatistics() {
    }

    public GlobalStatistics(String country, NewCase cases, NewDeath deaths, NewTests tests,  LocalDate date) {
        this.country = country;
        this.cases = cases;
        this.deaths = deaths;
        this.tests = tests;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public NewCase getCases() {
        return cases;
    }

    public void setCases(NewCase cases) {
        this.cases = cases;
    }

    public NewDeath getDeaths() {
        return deaths;
    }

    public void setDeaths(NewDeath deaths) {
        this.deaths = deaths;
    }

    public NewTests getTests() {
        return tests;
    }

    public void setTests(NewTests tests) {
        this.tests = tests;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "GlobalStatistics{" +
                "id=" + id +
                ", country='" + country + '\'' +
                ", cases=" + cases +
                ", deaths=" + deaths +
                ", tests=" + tests +
                '}';
    }
}
