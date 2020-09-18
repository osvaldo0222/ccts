package com.project.ccts.dto;

import java.time.LocalDateTime;

public class VisitTemplateDTO {
    private String uuid;
    private Float rssi;
    private Long timeArrived;
    private Long timeLeft;

    public VisitTemplateDTO(String uuid, Float rssi, Long timeArrived, Long timeLeft) {
        this.uuid = uuid;
        this.rssi = rssi;
        this.timeArrived = timeArrived;
        this.timeLeft = timeLeft;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Float getRssi() {
        return rssi;
    }

    public void setRssi(Float rssi) {
        this.rssi = rssi;
    }

    public Long getTimeArrived() {
        return timeArrived;
    }

    public void setTimeArrived(Long timeArrived) {
        this.timeArrived = timeArrived;
    }

    public Long getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(Long timeLeft) {
        this.timeLeft = timeLeft;
    }
}
