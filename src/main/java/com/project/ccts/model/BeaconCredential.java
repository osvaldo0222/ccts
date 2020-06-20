package com.project.ccts.model;

import com.project.ccts.util.enums.BeaconStatus;

import javax.persistence.*;

@Entity
public class BeaconCredential extends Credential {
    @Enumerated(EnumType.STRING)
    private BeaconStatus status;
    @OneToOne(mappedBy = "beaconCredential")
    private Beacon beacon;

    public BeaconStatus getStatus() {
        return status;
    }

    public void setStatus(BeaconStatus status) {
        this.status = status;
    }

    public Beacon getBeacon() {
        return beacon;
    }

    public void setBeacon(Beacon beacon) {
        this.beacon = beacon;
    }
}
