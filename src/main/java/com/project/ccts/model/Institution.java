package com.project.ccts.model;

import com.project.ccts.model.enums.InstitutionType;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Institution extends SampleLocality {
    @OneToMany
    private Collection<UserCredential> userCredentials;
    @Enumerated(EnumType.STRING)
    private InstitutionType type;

    public Collection<UserCredential> getUserCredentials() {
        return userCredentials;
    }

    public void setUserCredentials(Collection<UserCredential> userCredentials) {
        this.userCredentials = userCredentials;
    }

    public InstitutionType getType() {
        return type;
    }

    public void setType(InstitutionType type) {
        this.type = type;
    }
}
