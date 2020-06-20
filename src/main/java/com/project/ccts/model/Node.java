package com.project.ccts.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

@Entity
public class Node {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    //node-username
    private String nodeIdentifier;
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

    public String getNodeIdentifier() {
        return nodeIdentifier;
    }

    public void setNodeIdentifier(String uniqueIdentifier) {
        this.nodeIdentifier = uniqueIdentifier;
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

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", nodeIdentifier='" + nodeIdentifier + '\'' +
                ", locality=" + locality +
                ", gpsLocation=" + gpsLocation +
                ", nodeCredential=" + nodeCredential +
                '}';
    }
}
