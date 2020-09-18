package com.project.ccts.dto;

public class PersonExpositionStatusDTO {
    private Float percentage;
    private String comment;

    public PersonExpositionStatusDTO() {
    }

    public PersonExpositionStatusDTO(Float percentage, String comment) {
        this.percentage = percentage;
        this.comment = comment;
    }

    public Float getPercentage() {
        return percentage;
    }

    public void setPercentage(Float percentage) {
        this.percentage = percentage;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
