package com.project.ccts.model;

import javax.persistence.*;

@Entity
public class Node {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    private Locality locality;
    @OneToOne
    private GpsLocation gpsLocation;
    @OneToOne
    private NodeCredential nodeCredential;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Locality getLocality() {
        return locality;
    }

    public void setLocality(Locality locality) {
        this.locality = locality;
    }

    public GpsLocation getGpsLocation() {
        return gpsLocation;
    }

    public void setGpsLocation(GpsLocation gpsLocation) {
        this.gpsLocation = gpsLocation;
    }

    public NodeCredential getNodeCredential() {
        return nodeCredential;
    }

    public void setNodeCredential(NodeCredential nodeCredential) {
        this.nodeCredential = nodeCredential;
    }
}
