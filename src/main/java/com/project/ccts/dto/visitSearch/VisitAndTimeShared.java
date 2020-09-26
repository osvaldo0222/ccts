package com.project.ccts.dto.visitSearch;

import com.project.ccts.model.entities.Visit;

public class VisitAndTimeShared {
    private Visit visit;
    private Integer timeInMinutes;

    public VisitAndTimeShared(Visit visit, Integer timeInMinutes) {
        this.visit = visit;
        this.timeInMinutes = timeInMinutes;
    }

    public Visit getVisit() {
        return visit;
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
    }

    public Integer getTimeInMinutes() {
        return timeInMinutes;
    }

    public void setTimeInMinutes(Integer timeInMinutes) {
        this.timeInMinutes = timeInMinutes;
    }
}
