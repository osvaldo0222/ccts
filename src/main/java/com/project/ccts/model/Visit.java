package com.project.ccts.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Locality locality;
    @OneToMany
    private Collection<Beacon> beacons;
    private Date timeArrived;
    private Date timeLeft;
    @OneToOne
    private UserCredential user;
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

    public Locality getLocality() {
        return locality;
    }

    public void setLocality(Locality locality) {
        this.locality = locality;
    }

    public Collection<Beacon> getBeacons() {
        return beacons;
    }

    public void setBeacons(Collection<Beacon> beacons) {
        this.beacons = beacons;
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

    public UserCredential getUser() {
        return user;
    }

    public void setUser(UserCredential user) {
        this.user = user;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
