package com.project.ccts.dto.infected;

public class InfectedDetail {
    private String identifier;
    private Long personId;
    private double probabilityOfInfection;
    private long k;
    private String nameInfected;
    private String infectedId;
    private String telInfected;

    public InfectedDetail(String identifier, Long personId, double probabilityOfInfection, long k) {
        this.identifier = identifier;
        this.personId = personId;
        this.probabilityOfInfection = probabilityOfInfection;
        this.k = k;
    }

    public InfectedDetail(String identifier, Long personId, double probabilityOfInfection, long k, String nameInfected, String infectedId, String telInfected) {
        this.identifier = identifier;
        this.personId = personId;
        this.probabilityOfInfection = probabilityOfInfection;
        this.k = k;
        this.nameInfected = nameInfected;
        this.infectedId = infectedId;
        this.telInfected = telInfected;
    }

    public String getNameInfected() {
        return nameInfected;
    }

    public void setNameInfected(String nameInfected) {
        this.nameInfected = nameInfected;
    }

    public String getInfectedId() {
        return infectedId;
    }

    public void setInfectedId(String infectedId) {
        this.infectedId = infectedId;
    }

    public String getTelInfected() {
        return telInfected;
    }

    public void setTelInfected(String telInfected) {
        this.telInfected = telInfected;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public double getProbabilityOfInfection() {
        return probabilityOfInfection;
    }

    public void setProbabilityOfInfection(double probabilityOfInfection) {
        this.probabilityOfInfection = probabilityOfInfection;
    }

    public long getK() {
        return k;
    }

    public void setK(long k) {
        this.k = k;
    }
}
