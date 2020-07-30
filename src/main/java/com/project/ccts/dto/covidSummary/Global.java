package com.project.ccts.dto.covidSummary;

import com.project.ccts.dto.covidSummary.genericInfo.Cases;
import com.project.ccts.dto.covidSummary.genericInfo.Deaths;
import com.project.ccts.dto.covidSummary.genericInfo.Tests;

public class Global {

    private String country;
    private Cases cases;
    private Deaths deaths;
    private Tests tests;

    public Global(String country, Cases cases, Deaths deaths, Tests tests) {
        this.country = country;
        this.cases = cases;
        this.deaths = deaths;
        this.tests = tests;
    }

    public Global(){}

    @Override
    public String toString() {
        return "Global{" +
                "country='" + country + '\'' +
                ", cases=" + cases +
                ", deaths=" + deaths +
                ", tests=" + tests +
                '}';
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Cases getCases() {
        return cases;
    }

    public void setCases(Cases cases) {
        this.cases = cases;
    }

    public Deaths getDeaths() {
        return deaths;
    }

    public void setDeaths(Deaths deaths) {
        this.deaths = deaths;
    }

    public Tests getTests() {
        return tests;
    }

    public void setTests(Tests tests) {
        this.tests = tests;
    }
}

