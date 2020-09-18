package com.project.ccts.model.entities;

import javax.persistence.*;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Entity
public class UserCredential extends Credential {
    @Column(nullable = false, unique = true)
    private UUID uuid;
    private String validationCode;
    @OneToMany(mappedBy = "userCredential")
    private Set<NotificationToken> notificationToken;
    @OneToMany(mappedBy = "userCredential")
    private Collection<Notification> notifications;
    @OneToOne(mappedBy = "userCredential", fetch = FetchType.LAZY)
    private Person person;

    public UserCredential() { }

    public UserCredential(String username, String password, Boolean authenticated, Person person, Collection<Role> roles) {
        super(username, password, authenticated);
        this.person = person;
        this.roles = roles;
    }
    public UserCredential(String username, String password, Boolean authenticated) {
        super(username, password, authenticated);

    }

    @PrePersist
    public void autofill() {
        setUuid(new UUID(1934084230108169705L, UUID.randomUUID().getLeastSignificantBits()));
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

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

    public Collection<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(Collection<Notification> notifications) {
        this.notifications = notifications;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
