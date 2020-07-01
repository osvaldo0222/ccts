package com.project.ccts.dto;

import com.project.ccts.model.enums.BeaconStatus;

public class BeaconDetailsDTO {
    private Long id;
    private String beaconIdentifier;
    private BeaconStatus status;

    public BeaconDetailsDTO(Long id, String beaconIdentifier, BeaconStatus status) {
        this.id = id;
        this.beaconIdentifier = beaconIdentifier;

        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBeaconIdentifier() {
        return beaconIdentifier;
    }

    public void setBeaconIdentifier(String nodeIdentifier) {
        this.beaconIdentifier = nodeIdentifier;
    }

    public BeaconStatus getStatus() {
        return status;
    }

    public void setStatus(BeaconStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BeaconDetailsDTO{" +
                "id=" + id +
                ", beaconIdentifier='" + beaconIdentifier + '\'' +
                ", status=" + status +
                '}';
    }
}
