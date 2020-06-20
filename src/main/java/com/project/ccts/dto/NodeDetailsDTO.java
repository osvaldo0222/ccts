package com.project.ccts.dto;

import com.project.ccts.model.GpsLocation;
import com.project.ccts.util.enums.BeaconStatus;

public class NodeDetailsDTO {
    private Long id;
    private String nodeIdentifier;
    private GpsLocation gpsLocation;
    private BeaconStatus status;

    public NodeDetailsDTO(Long id, String nodeIdentifier, GpsLocation gpsLocation, BeaconStatus status) {
        this.id = id;
        this.nodeIdentifier = nodeIdentifier;
        this.gpsLocation = gpsLocation;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNodeIdentifier() {
        return nodeIdentifier;
    }

    public void setNodeIdentifier(String nodeIdentifier) {
        this.nodeIdentifier = nodeIdentifier;
    }

    public GpsLocation getGpsLocation() {
        return gpsLocation;
    }

    public void setGpsLocation(GpsLocation gpsLocation) {
        this.gpsLocation = gpsLocation;
    }

    public BeaconStatus getStatus() {
        return status;
    }

    public void setStatus(BeaconStatus status) {
        this.status = status;
    }
}
