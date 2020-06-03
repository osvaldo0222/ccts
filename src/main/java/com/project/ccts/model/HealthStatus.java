package com.project.ccts.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
public class HealthStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    private Date statusDate;
    @OneToOne
    private Test test;
    @ManyToOne(fetch = FetchType.LAZY)
    private Person person;
    private boolean fever;
    private boolean cough;
    private boolean breathDifficulty;
    private boolean soreThroat;
    private boolean smellLoss;
    private boolean tasteLoss;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
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
}
