package com.project.ccts.dto;

import java.util.Date;
import java.util.List;

public class ExpositionDetailDTO {
    private Long id;
    private Integer k;
    private Double probabilityOfInfection;
    private Date date;
    private List<VisitMobileDTO> visit;

    public ExpositionDetailDTO() {
    }

    public ExpositionDetailDTO(Long id, Integer k, Double probabilityOfInfection, Date date, List<VisitMobileDTO> visit) {
        this.id = id;
        this.k = k;
        this.probabilityOfInfection = probabilityOfInfection;
        this.date = date;
        this.visit = visit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<VisitMobileDTO> getVisit() {
        return visit;
    }

    public void setVisit(List<VisitMobileDTO> visit) {
        this.visit = visit;
    }
}
