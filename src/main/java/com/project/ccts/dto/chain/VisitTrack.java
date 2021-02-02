package com.project.ccts.dto.chain;

import java.time.LocalDateTime;

public class VisitTrack {
    private LocalDateTime timeArrived;
    private LocalDateTime timeLeft;
    private Long elapsedTime;
    private String name;
    private Long id;

    public VisitTrack(LocalDateTime timeArrived, LocalDateTime timeLeft, Long elapsedTime, String name, Long id) {
        this.timeArrived = timeArrived;
        this.timeLeft = timeLeft;
        this.elapsedTime = elapsedTime;
        this.name = name;
        this.id = id;
    }

    public Long getElapsedTime() {
        return elapsedTime;
    }

    public void setElapsedTime(Long elapsedTime) {
        this.elapsedTime = elapsedTime;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
