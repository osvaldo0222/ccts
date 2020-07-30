package com.project.ccts.dto;

import com.project.ccts.model.entities.Address;
import com.project.ccts.model.entities.GpsLocation;

public class NodeConfigDTO {
    private String localityName;
    private Address localityAddress;
    private String nodeDescription;
    private GpsLocation gpsLocation;

    public NodeConfigDTO(String localityName, Address localityAddress, String nodeDescription, GpsLocation gpsLocation) {
        this.localityName = localityName;
        this.localityAddress = localityAddress;
        this.nodeDescription = nodeDescription;
        this.gpsLocation = gpsLocation;
    }

    public String getLocalityName() {
        return localityName;
    }

    public void setLocalityName(String localityName) {
        this.localityName = localityName;
    }

    public Address getLocalityAddress() {
        return localityAddress;
    }

    public void setLocalityAddress(Address localityAddress) {
        this.localityAddress = localityAddress;
    }

    public String getNodeDescription() {
        return nodeDescription;
    }

    public void setNodeDescription(String nodeDescription) {
        this.nodeDescription = nodeDescription;
    }

    public GpsLocation getGpsLocation() {
        return gpsLocation;
    }

    public void setGpsLocation(GpsLocation gpsLocation) {
        this.gpsLocation = gpsLocation;
    }
}
