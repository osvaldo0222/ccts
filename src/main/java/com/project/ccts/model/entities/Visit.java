package com.project.ccts.model.entities;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Node node;
    @ManyToOne
    private Person person;
    private Date timeArrived;
    private Date timeLeft;
    private Float minimunDistance;
    @CreationTimestamp
    private Date creationDate;

    public Visit() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTimeArrived() {
        return timeArrived;
    }

    public void setTimeArrived(Date timeArrived) {
        this.timeArrived = timeArrived;
    }

    public Date getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(Date timeLeft) {
        this.timeLeft = timeLeft;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
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
}
