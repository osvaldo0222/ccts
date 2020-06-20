package com.project.ccts.model;

import javax.persistence.*;

@Entity
public class Beacon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String instance;
    @ManyToOne
    private Locality locality;
    @OneToOne
    private BeaconCredential beaconCredential;
    private Float batteryLevel;
    private Integer packetSend;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String uniqueIdentifier) {
        this.instance = uniqueIdentifier;
    }

    public Locality getLocality() {
        return locality;
    }

    public void setLocality(Locality locality) {
        this.locality = locality;
    }

    public BeaconCredential getBeaconCredential() {
        return beaconCredential;
    }

    public void setBeaconCredential(BeaconCredential beaconCredential) {
        this.beaconCredential = beaconCredential;
    }

    public Float getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(Float batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public Integer getPacketSend() {
        return packetSend;
    }

    public void setPacketSend(Integer packetSend) {
        this.packetSend = packetSend;
    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", nodeIdentifier='" + instance + '\'' +
                ", locality=" + locality +
                ", nodeCredential=" + beaconCredential +
                '}';
    }
}
