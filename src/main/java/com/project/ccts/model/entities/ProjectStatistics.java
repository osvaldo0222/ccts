package com.project.ccts.model.entities;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class ProjectStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long registeredTest;
    private Long confirmedCases;
    private Long confirmedCasesToday;
    private Long messageReceivedNodes;
    private Long messageReceivedNodesToday;
    private Long notificationsSent;
    private Long notificationsSentToday;
    private Long maleInfected;
    private Long maleInfectedToday;
    private Long femaleInfected;
    private Long femaleInfectedToday;
    private Long nodeRegistered;
    @CreationTimestamp
    private LocalDate localDate;

    public ProjectStatistics() {
        this.registeredTest = 0L;
        this.confirmedCases = 0L;
        this.confirmedCasesToday = 0L;
        this.messageReceivedNodes = 0L;
        this.messageReceivedNodesToday = 0L;
        this.notificationsSent = 0L;
        this.notificationsSentToday = 0L;
        this.maleInfected = 0L;
        this.maleInfectedToday = 0L;
        this.femaleInfected = 0L;
        this.femaleInfectedToday = 0L;
        this.nodeRegistered = 0L;
    }

    public ProjectStatistics(Long registeredTest, Long confirmedCases, Long confirmedCasesToday, Long messageReceivedNodes, Long messageReceivedNodesToday, Long notificationsSent, Long notificationsSentToday, Long maleInfected, Long maleInfectedToday, Long femaleInfected, Long femaleInfectedToday, Long nodeRegistered) {
        this.registeredTest = registeredTest;
        this.confirmedCases = confirmedCases;
        this.confirmedCasesToday = confirmedCasesToday;
        this.messageReceivedNodes = messageReceivedNodes;
        this.messageReceivedNodesToday = messageReceivedNodesToday;
        this.notificationsSent = notificationsSent;
        this.notificationsSentToday = notificationsSentToday;
        this.maleInfected = maleInfected;
        this.maleInfectedToday = maleInfectedToday;
        this.femaleInfected = femaleInfected;
        this.femaleInfectedToday = femaleInfectedToday;
        this.nodeRegistered = nodeRegistered;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRegisteredTest() {
        return registeredTest;
    }

    public void setRegisteredTest(Long registeredTest) {
        this.registeredTest = registeredTest;
    }

    public Long getConfirmedCases() {
        return confirmedCases;
    }

    public void setConfirmedCases(Long confirmedCases) {
        this.confirmedCases = confirmedCases;
    }

    public Long getConfirmedCasesToday() {
        return confirmedCasesToday;
    }

    public void setConfirmedCasesToday(Long confirmedCasesToday) {
        this.confirmedCasesToday = confirmedCasesToday;
    }

    public Long getMessageReceivedNodes() {
        return messageReceivedNodes;
    }

    public void setMessageReceivedNodes(Long messageReceivedNodes) {
        this.messageReceivedNodes = messageReceivedNodes;
    }

    public Long getMessageReceivedNodesToday() {
        return messageReceivedNodesToday;
    }

    public void setMessageReceivedNodesToday(Long messageReceivedNodesToday) {
        this.messageReceivedNodesToday = messageReceivedNodesToday;
    }

    public Long getNotificationsSent() {
        return notificationsSent;
    }

    public void setNotificationsSent(Long notificationsSent) {
        this.notificationsSent = notificationsSent;
    }

    public Long getNotificationsSentToday() {
        return notificationsSentToday;
    }

    public void setNotificationsSentToday(Long notificationsSentToday) {
        this.notificationsSentToday = notificationsSentToday;
    }

    public Long getMaleInfected() {
        return maleInfected;
    }

    public void setMaleInfected(Long maleInfected) {
        this.maleInfected = maleInfected;
    }

    public Long getMaleInfectedToday() {
        return maleInfectedToday;
    }

    public void setMaleInfectedToday(Long maleInfectedToday) {
        this.maleInfectedToday = maleInfectedToday;
    }

    public Long getFemaleInfected() {
        return femaleInfected;
    }

    public void setFemaleInfected(Long femaleInfected) {
        this.femaleInfected = femaleInfected;
    }

    public Long getFemaleInfectedToday() {
        return femaleInfectedToday;
    }

    public void setFemaleInfectedToday(Long femaleInfectedToday) {
        this.femaleInfectedToday = femaleInfectedToday;
    }

    public Long getNodeRegistered() {
        return nodeRegistered;
    }

    public void setNodeRegistered(Long nodeRegistered) {
        this.nodeRegistered = nodeRegistered;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }
}
