package com.project.ccts.model.entities;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class ProvinceStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long infected;
    private Long deaths;
    private Long recovered;
    private LocalDate date;
    @ManyToOne(fetch = FetchType.LAZY)
    private Province province;

    public ProvinceStatistics() {
        this.infected = 0L;
        this.deaths = 0L;
        this.recovered = 0L;
    }

    public ProvinceStatistics(Long infected, Long deaths, Long recovered, LocalDate date, Province province) {
        this.infected = infected;
        this.deaths = deaths;
        this.recovered = recovered;
        this.date = date;
        this.province = province;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInfected() {
        return infected;
    }

    public void setInfected(Long infected) {
        this.infected = infected;
    }

    public Long getDeaths() {
        return deaths;
    }

    public void setDeaths(Long deaths) {
        this.deaths = deaths;
    }

    public Long getRecovered() {
        return recovered;
    }

    public void setRecovered(Long recovered) {
        this.recovered = recovered;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }
}
