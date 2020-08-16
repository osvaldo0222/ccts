package com.project.ccts.dto;

import com.project.ccts.model.entities.Address;
import com.project.ccts.model.entities.GpsLocation;

public class LocalityMobileDTO {
    private String name;
    private Address address;
    private GpsLocation gpsLocation;
    private Integer nodeCount;
    private Integer confirmCases;
    private Integer suspectsCases;

    public LocalityMobileDTO() {
    }

    public LocalityMobileDTO(String name, Address address, GpsLocation gpsLocation, Integer nodeCount, Integer confirmCases, Integer suspectsCases) {
        this.name = name;
        this.address = address;
        this.gpsLocation = gpsLocation;
        this.nodeCount = nodeCount;
        this.confirmCases = confirmCases;
        this.suspectsCases = suspectsCases;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public GpsLocation getGpsLocation() {
        return gpsLocation;
    }

    public void setGpsLocation(GpsLocation gpsLocation) {
        this.gpsLocation = gpsLocation;
    }

    public Integer getNodeCount() {
        return nodeCount;
    }

    public void setNodeCount(Integer nodeCount) {
        this.nodeCount = nodeCount;
    }

    public Integer getConfirmCases() {
        return confirmCases;
    }

    public void setConfirmCases(Integer confirmCases) {
        this.confirmCases = confirmCases;
    }

    public Integer getSuspectsCases() {
        return suspectsCases;
    }

    public void setSuspectsCases(Integer suspectsCases) {
        this.suspectsCases = suspectsCases;
    }
}
