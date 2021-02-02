package com.project.ccts.dto.chain;

import com.project.ccts.dto.infected.InfectedDetail;

import java.util.Collection;

public class InfectionChainDetail {
    private Status status;
    private Collection<InfectedDetail> infectedDetails;
    private Collection<VisitTrack> visitTrack;


    public InfectionChainDetail(Status status, Collection<InfectedDetail> infectedDetails, Collection<VisitTrack> visitTrack) {
        this.status = status;
        this.infectedDetails = infectedDetails;
        this.visitTrack = visitTrack;
    }

    public Collection<VisitTrack> getVisitTrack() {
        return visitTrack;
    }

    public void setVisitTrack(Collection<VisitTrack> visitTrack) {
        this.visitTrack = visitTrack;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Collection<InfectedDetail> getInfectedDetails() {
        return infectedDetails;
    }

    public void setInfectedDetails(Collection<InfectedDetail> infectedDetails) {
        this.infectedDetails = infectedDetails;
    }
}
