package com.project.ccts.dto.chain;

import java.util.Date;

public class Status {
    private boolean fever;
    private boolean cough;
    private boolean breathDifficulty;
    private boolean soreThroat;
    private boolean smellLoss;
    private boolean tasteLoss;
    private Date statusDate;
    private Boolean status;

    public Status(boolean fever, boolean cough, boolean breathDifficulty, boolean soreThroat, boolean smellLoss, boolean tasteLoss, Date statusDate, Boolean status) {
        this.fever = fever;
        this.cough = cough;
        this.breathDifficulty = breathDifficulty;
        this.soreThroat = soreThroat;
        this.smellLoss = smellLoss;
        this.tasteLoss = tasteLoss;
        this.statusDate = statusDate;
        this.status = status;
    }

    public boolean isFever() {
        return fever;
    }

    public void setFever(boolean fever) {
        this.fever = fever;
    }

    public boolean isCough() {
        return cough;
    }

    public void setCough(boolean cough) {
        this.cough = cough;
    }

    public boolean isBreathDifficulty() {
        return breathDifficulty;
    }

    public void setBreathDifficulty(boolean breathDifficulty) {
        this.breathDifficulty = breathDifficulty;
    }

    public boolean isSoreThroat() {
        return soreThroat;
    }

    public void setSoreThroat(boolean soreThroat) {
        this.soreThroat = soreThroat;
    }

    public boolean isSmellLoss() {
        return smellLoss;
    }

    public void setSmellLoss(boolean smellLoss) {
        this.smellLoss = smellLoss;
    }

    public boolean isTasteLoss() {
        return tasteLoss;
    }

    public void setTasteLoss(boolean tasteLoss) {
        this.tasteLoss = tasteLoss;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
