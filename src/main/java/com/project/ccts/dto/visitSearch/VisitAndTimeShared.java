package com.project.ccts.dto.visitSearch;

import com.project.ccts.model.entities.Visit;

public class VisitAndTimeShared {
    private Visit visit;
    private Long timeInMinutes;

    public VisitAndTimeShared(Visit visit, Long timeInMinutes) {
        this.visit = visit;
        this.timeInMinutes = timeInMinutes;
    }

    public Visit getVisit() {
        return visit;
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
    }

    public Long getTimeInMinutes() {
        return timeInMinutes;
    }

    public void setTimeInMinutes(Long timeInMinutes) {
        this.timeInMinutes = timeInMinutes;
    }

    @Override
    public String toString() {
        return "VisitAndTimeShared{" +
                "visit=" + visit +
                ", timeInMinutes=" + timeInMinutes +
                '}';
    }
}
