package com.project.ccts.model.entities;

import javax.persistence.Embeddable;

@Embeddable
public class NotificationData {
    private String navigate;
    private String screen;

    public NotificationData() {
    }

    public NotificationData(String navigate) {
        this.navigate = navigate;
    }

    public NotificationData(String navigate, String screen) {
        this.navigate = navigate;
        this.screen = screen;
    }

    public String getNavigate() {
        return navigate;
    }

    public void setNavigate(String navigate) {
        this.navigate = navigate;
    }

    public String getScreen() {
        return screen;
    }

    public void setScreen(String screen) {
        this.screen = screen;
    }
}
