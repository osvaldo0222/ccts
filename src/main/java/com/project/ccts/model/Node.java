package com.project.ccts.model;

import javax.persistence.*;

@Entity
public class Node {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String uniqueIdentifier;
    @ManyToOne
    private Locality locality;
    @Embedded
    private GpsLocation gpsLocation;
    @OneToOne
    private NodeCredential nodeCredential;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUniqueIdentifier() {
        return uniqueIdentifier;
    }

    public void setUniqueIdentifier(String uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier;
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
