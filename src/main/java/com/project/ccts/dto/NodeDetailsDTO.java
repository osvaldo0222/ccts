package com.project.ccts.dto;

import com.project.ccts.model.enums.NodeStatus;

public class NodeDetailsDTO {
    private Long id;
    private String beaconIdentifier;
    private NodeStatus status;

    public NodeDetailsDTO(Long id, String beaconIdentifier, NodeStatus status) {
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

    public NodeStatus getStatus() {
        return status;
    }

    public void setStatus(NodeStatus status) {
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
