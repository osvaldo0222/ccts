package com.project.ccts.model.entities;

import javax.persistence.Embeddable;

@Embeddable
public class NotificationData {
    private String navigate;

    public NotificationData() {
    }

    public NotificationData(String navigate) {
        this.navigate = navigate;
    }

    public String getNavigate() {
        return navigate;
    }

    public void setNavigate(String navigate) {
        this.navigate = navigate;
    }
}
