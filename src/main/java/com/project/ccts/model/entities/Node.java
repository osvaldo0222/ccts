package com.project.ccts.model.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Node {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String nodeIdentifier;
    private String description;
    @ManyToOne
    private Locality locality;
    @OneToOne(cascade = CascadeType.ALL)
    private NodeCredential nodeCredential;
    private Float batteryLevel;
    @OneToMany(mappedBy = "node")
    private Collection<Visit> visits;

    public Node(){}

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

    public NodeCredential getNodeCredential() {
        return nodeCredential;
    }

    public void setNodeCredential(NodeCredential nodeCredential) {
        this.nodeCredential = nodeCredential;
    }

    public Float getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(Float batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public String getNodeIdentifier() {
        return nodeIdentifier;
    }

    public void setNodeIdentifier(String nodeIdentifier) {
        this.nodeIdentifier = nodeIdentifier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Collection<Visit> getVisits() {
        return visits;
    }

    public void setVisits(Collection<Visit> visits) {
        this.visits = visits;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", nodeIdentifier='" + nodeIdentifier + '\'' +
                ", locality=" + locality +
                ", nodeCredential=" + nodeCredential +
                '}';
    }
}
