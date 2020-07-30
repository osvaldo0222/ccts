package com.project.ccts.model.entities;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Locality locality;
    @ManyToMany
    private Collection<Node> nodes;
    @ManyToOne
    private Person person;
    private LocalDateTime timeArrived;
    private LocalDateTime timeLeft;
    private Float minimunDistance;
    @CreationTimestamp
    private LocalDateTime creationDate;

    public Visit() {
    }

    public Visit(Collection<Node> nodes, Locality locality, Person person, LocalDateTime timeArrived, LocalDateTime timeLeft, Float minimunDistance) {
        this.locality = locality;
        this.nodes = nodes;
        this.person = person;
        this.timeArrived = timeArrived;
        this.timeLeft = timeLeft;
        this.minimunDistance = minimunDistance;
    }

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

    public LocalDateTime getTimeArrived() {
        return timeArrived;
    }

    public void setTimeArrived(LocalDateTime timeArrived) {
        this.timeArrived = timeArrived;
    }

    public LocalDateTime getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(LocalDateTime timeLeft) {
        this.timeLeft = timeLeft;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Collection<Node> getNodes() {
        return nodes;
    }

    public void setNode(Collection<Node> nodes) {
        this.nodes = nodes;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person user) {
        this.person = user;
    }

    public Float getMinimunDistance() {
        return minimunDistance;
    }

    public void setMinimunDistance(Float minimunDistance) {
        this.minimunDistance = minimunDistance;
    }

    public void addNode(Node node) {
        for (Node aux : nodes) {
            if (aux.getNodeIdentifier().equals(node.getNodeIdentifier())) {
                return;
            }
        }
        nodes.add(node);
    }
}
