package com.project.ccts.dto;

import java.time.LocalDateTime;
import java.util.Collection;

public class VisitMobileDTO {
    private Long id;
    private LocalityMobileDTO locality;
    private Collection<NodeMobileDTO> nodes;
    private LocalDateTime timeArrived;
    private LocalDateTime timeLeft;
    private Float expositionProbability;

    public VisitMobileDTO() {
    }

    public VisitMobileDTO(Long id, LocalityMobileDTO locality, Collection<NodeMobileDTO> nodes, LocalDateTime timeArrived, LocalDateTime timeLeft, Float expositionProbability) {
        this.id = id;
        this.locality = locality;
        this.nodes = nodes;
        this.timeArrived = timeArrived;
        this.timeLeft = timeLeft;
        this.expositionProbability = expositionProbability;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalityMobileDTO getLocality() {
        return locality;
    }

    public void setLocality(LocalityMobileDTO locality) {
        this.locality = locality;
    }

    public Collection<NodeMobileDTO> getNodes() {
        return nodes;
    }

    public void setNodes(Collection<NodeMobileDTO> nodes) {
        this.nodes = nodes;
    }

    public LocalDateTime getTimeArrived() {
        return timeArrived;
    }

    public void setTimeArrived(LocalDateTime timeArrived) {
        this.timeArrived = timeArrived;
    }

    public LocalDateTime getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(LocalDateTime timeLeft) {
        this.timeLeft = timeLeft;
    }

    public Float getExpositionProbability() {
        return expositionProbability;
    }

    public void setExpositionProbability(Float expositionProbability) {
        this.expositionProbability = expositionProbability;
    }
}
