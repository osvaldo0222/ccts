package com.project.ccts.dto;

public class PersonHealthStatusUpdateDTO {
    private String id;
    private boolean status;
    private boolean fever;
    private boolean cough;
    private boolean breathDifficulty;
    private boolean soreThroat;
    private boolean smellLoss;
    private boolean tasteLoss;

    public PersonHealthStatusUpdateDTO(String id, boolean status, boolean fever, boolean cough, boolean breathDifficulty, boolean soreThroat, boolean smellLoss, boolean tasteLoss) {
        this.id = id;
        this.status = status;
        this.fever = fever;
        this.cough = cough;
        this.breathDifficulty = breathDifficulty;
        this.soreThroat = soreThroat;
        this.smellLoss = smellLoss;
        this.tasteLoss = tasteLoss;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
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

    @Override
    public String toString() {
        return "PersonHealthStatusUpdateDTO{" +
                "id='" + id + '\'' +
                ", status=" + status +
                ", fever=" + fever +
                ", cough=" + cough +
                ", breathDifficulty=" + breathDifficulty +
                ", soreThroat=" + soreThroat +
                ", smellLoss=" + smellLoss +
                ", tasteLoss=" + tasteLoss +
                '}';
    }
}
