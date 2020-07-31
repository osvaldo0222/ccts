package com.project.ccts.dto;

public class HealthStatusMobileAddDTO {
    private Boolean fever;
    private Boolean cough;
    private Boolean breathDifficulty;
    private Boolean soreThroat;
    private Boolean smellLoss;
    private Boolean tasteLoss;

    public HealthStatusMobileAddDTO() {
    }

    public HealthStatusMobileAddDTO(Boolean fever, Boolean cough, Boolean breathDifficulty, Boolean soreThroat, Boolean smellLoss, Boolean tasteLoss) {
        this.fever = fever;
        this.cough = cough;
        this.breathDifficulty = breathDifficulty;
        this.soreThroat = soreThroat;
        this.smellLoss = smellLoss;
        this.tasteLoss = tasteLoss;
    }

    public Boolean getFever() {
        return fever;
    }

    public void setFever(Boolean fever) {
        this.fever = fever;
    }

    public Boolean getCough() {
        return cough;
    }

    public void setCough(Boolean cough) {
        this.cough = cough;
    }

    public Boolean getBreathDifficulty() {
        return breathDifficulty;
    }

    public void setBreathDifficulty(Boolean breathDifficulty) {
        this.breathDifficulty = breathDifficulty;
    }

    public Boolean getSoreThroat() {
        return soreThroat;
    }

    public void setSoreThroat(Boolean soreThroat) {
        this.soreThroat = soreThroat;
    }

    public Boolean getSmellLoss() {
        return smellLoss;
    }

    public void setSmellLoss(Boolean smellLoss) {
        this.smellLoss = smellLoss;
    }

    public Boolean getTasteLoss() {
        return tasteLoss;
    }

    public void setTasteLoss(Boolean tasteLoss) {
        this.tasteLoss = tasteLoss;
    }
}
