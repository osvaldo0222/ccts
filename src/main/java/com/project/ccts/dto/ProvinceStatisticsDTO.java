package com.project.ccts.dto;

import java.time.LocalDate;

public class ProvinceStatisticsDTO {
    private String provinceName;
    private Long infected;
    private Long deaths;
    private Long recovered;
    private LocalDate date;

    public ProvinceStatisticsDTO() {
    }

    public ProvinceStatisticsDTO(String provinceName, Long infected, Long deaths, Long recovered, LocalDate date) {
        this.provinceName = provinceName;
        this.infected = infected;
        this.deaths = deaths;
        this.recovered = recovered;
        this.date = date;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
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
}
