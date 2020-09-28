package com.project.ccts.dto;

public class PersonExpositionStatusDTO {
    private Double percentage;
    private String comment;
    private Integer contacts;

    public PersonExpositionStatusDTO() {
    }

    public PersonExpositionStatusDTO(Double percentage, String comment, Integer contacts) {
        this.percentage = percentage;
        this.comment = comment;
        this.contacts = contacts;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getContacts() {
        return contacts;
    }

    public void setContacts(Integer contacts) {
        this.contacts = contacts;
    }
}
