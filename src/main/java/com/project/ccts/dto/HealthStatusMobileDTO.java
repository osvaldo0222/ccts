package com.project.ccts.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class HealthStatusMobileDTO {
    private Long id;
    private LocalDateTime statusDate;
    private Boolean status;
    private Boolean fever;
    private Boolean cough;
    private Boolean breathDifficulty;
    private Boolean soreThroat;
    private Boolean smellLoss;
    private Boolean tasteLoss;
    private Set<String> recommendations;

    public HealthStatusMobileDTO(Long id, LocalDateTime statusDate, Boolean status, Boolean fever, Boolean cough, Boolean breathDifficulty, Boolean soreThroat, Boolean smellLoss, Boolean tasteLoss, Set<String> recommendations) {
        this.id = id;
        this.statusDate = statusDate;
        this.status = status;
        this.fever = fever;
        this.cough = cough;
        this.breathDifficulty = breathDifficulty;
        this.soreThroat = soreThroat;
        this.smellLoss = smellLoss;
        this.tasteLoss = tasteLoss;
        this.recommendations = recommendations;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(LocalDateTime statusDate) {
        this.statusDate = statusDate;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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

    public Set<String> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(Set<String> recommendations) {
        this.recommendations = recommendations;
    }
}
