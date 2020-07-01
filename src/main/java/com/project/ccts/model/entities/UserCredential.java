package com.project.ccts.model.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import java.util.Set;

@Entity
public class UserCredential extends Credential {
    private String validationCode;
    @OneToMany(mappedBy = "userCredential")
    private Set<NotificationToken> notificationToken;
    @OneToOne(mappedBy = "userCredential", fetch = FetchType.LAZY)
    private Person person;

    public String getValidationCode() {
        return validationCode;
    }

    public void setValidationCode(String validationCode) {
        this.validationCode = validationCode;
    }

    public Set<NotificationToken> getNotificationToken() {
        return notificationToken;
    }

    public void setNotificationToken(Set<NotificationToken> notificationToken) {
        this.notificationToken = notificationToken;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
