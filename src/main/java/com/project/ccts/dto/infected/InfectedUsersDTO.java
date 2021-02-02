package com.project.ccts.dto.infected;

import java.time.LocalDateTime;
import java.util.Date;

public class InfectedUsersDTO {
    private Long personId;
    private String identifier;
    private Date healthTestDate;
    private String institutionName;

    public InfectedUsersDTO(Long personId, String identifier, Date testDate, String institutionName) {
        this.personId = personId;
        this.identifier = identifier;
        this.healthTestDate = testDate;
        this.institutionName = institutionName;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Date getHealthTestDate() {
        return healthTestDate;
    }

    public void setHealthTestDate(Date healthTestDate) {
        this.healthTestDate = healthTestDate;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }
}
