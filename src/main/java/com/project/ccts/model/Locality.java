package com.project.ccts.model;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Collection;

@Entity
public class Locality extends SampleLocality {
    @OneToMany(mappedBy = "locality")
    private Collection<Beacon> beacons;
    @OneToMany(mappedBy = "locality")
    private Collection<Visit> visits;
    @Embedded
    private GpsLocation gpsLocation;
    private String beaconsNamespace;

    public Collection<Beacon> getBeacons() {
        return beacons;
    }

    public void setBeacons(Collection<Beacon> beacons) {
        this.beacons = beacons;
    }

    public Collection<Visit> getVisits() {
        return visits;
    }

    public void setVisits(Collection<Visit> visits) {
        this.visits = visits;
    }

    public GpsLocation getGpsLocation() {
        return gpsLocation;
    }

    public void setGpsLocation(GpsLocation gpsLocation) {
        this.gpsLocation = gpsLocation;
    }

    public String getBeaconsNamespace() {
        return beaconsNamespace;
    }

    public void setBeaconsNamespace(String beaconsNamespace) {
        this.beaconsNamespace = beaconsNamespace;
    }
}
