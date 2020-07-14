package com.project.ccts.dto;

import com.project.ccts.model.enums.NodeStatus;

public class NodeDetailsDTO {
    private Long id;
    private String beaconIdentifier;
    private Float batteryLevel;
    private Integer visits;
    private NodeStatus nodeStatus;


    public NodeDetailsDTO(Long id, String beaconIdentifier, Float batteryLevel, Integer visits,NodeStatus nodeStatus) {
        this.id = id;
        this.beaconIdentifier = beaconIdentifier;
        this.batteryLevel = batteryLevel;
        this.visits = visits;
        this.nodeStatus = nodeStatus;
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

    public Float getBatteryLevel() {
        return batteryLevel;
    }

    public void setBatteryLevel(Float batteryLevel) {
        this.batteryLevel = batteryLevel;
    }

    public Integer getVisits() {
        return visits;
    }

    public void setVisits(Integer visits) {
        this.visits = visits;
    }

    public NodeStatus getNodeStatus() {
        return nodeStatus;
    }

    public void setNodeStatus(NodeStatus nodeStatus) {
        this.nodeStatus = nodeStatus;
    }

    @Override
    public String toString() {
        return "BeaconDetailsDTO{" +
                "id=" + id +
                ", beaconIdentifier='" + beaconIdentifier + '\'' +
               
                '}';
    }
}
