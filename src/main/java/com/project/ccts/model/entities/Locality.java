package com.project.ccts.model.entities;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class Locality extends SampleLocality {
    @OneToMany(mappedBy = "locality",cascade = CascadeType.ALL)
    private Collection<Beacon> beacons;
    @OneToMany(mappedBy = "locality")
    private Collection<Visit> visits;
    @Embedded
    private GpsLocation gpsLocation;
    private String beaconsNamespace;

    public Locality(String name, Address address, String email, String cellPhone,String beaconsNamespace) {
        super(name, address, email, cellPhone);
        this.beaconsNamespace = beaconsNamespace;
    }
    public void addBeacon(Beacon beacon){
        if (getBeacons() == null){
            this.beacons = new ArrayList<>();
        }
        this.beacons.add(beacon);
    }
    public Locality(){
        super();
    }

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
