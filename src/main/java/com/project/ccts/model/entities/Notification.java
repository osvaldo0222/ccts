package com.project.ccts.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @JsonIgnore
    private String subtitle;
    private String messageBody;
    @Embedded
    private NotificationData data;
    @CreationTimestamp
    private LocalDateTime sendDate;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private UserCredential userCredential;

    public Notification() {
    }

    public Notification(String title, String subtitle, String messageBody, NotificationData data, UserCredential userCredential) {
        this.title = title;
        this.subtitle = subtitle;
        this.messageBody = messageBody;
        this.data = data;
        this.userCredential = userCredential;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public NotificationData getData() {
        return data;
    }

    public void setData(NotificationData data) {
        this.data = data;
    }

    public LocalDateTime getSendDate() {
        return sendDate;
    }

    public void setSendDate(LocalDateTime sendDate) {
        this.sendDate = sendDate;
    }

    public UserCredential getUserCredential() {
        return userCredential;
    }

    public void setUserCredential(UserCredential userCredential) {
        this.userCredential = userCredential;
    }
}
