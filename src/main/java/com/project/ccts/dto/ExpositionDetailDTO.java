package com.project.ccts.dto;

import java.time.LocalDateTime;

public class ExpositionDetailDTO {
    private Long id;
    private String comment;
    private Boolean exposition;
    private LocalDateTime date;
    private VisitMobileDTO visit;

    public ExpositionDetailDTO() {
    }

    public ExpositionDetailDTO(Long id, String comment, Boolean exposition, LocalDateTime date, VisitMobileDTO visit) {
        this.id = id;
        this.comment = comment;
        this.exposition = exposition;
        this.date = date;
        this.visit = visit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getExposition() {
        return exposition;
    }

    public void setExposition(Boolean exposition) {
        this.exposition = exposition;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public VisitMobileDTO getVisit() {
        return visit;
    }

    public void setVisit(VisitMobileDTO visit) {
        this.visit = visit;
    }
}
