package com.project.ccts.dto.projectStatistics;

import com.project.ccts.model.entities.GpsLocation;

public class NodeLocationMarker {
    private GpsLocation gpsLocation;
    private String name;

    public NodeLocationMarker(GpsLocation gpsLocation, String name) {
        this.gpsLocation = gpsLocation;
        this.name = name;
    }

    public GpsLocation getGpsLocation() {
        return gpsLocation;
    }

    public void setGpsLocation(GpsLocation gpsLocation) {
        this.gpsLocation = gpsLocation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
