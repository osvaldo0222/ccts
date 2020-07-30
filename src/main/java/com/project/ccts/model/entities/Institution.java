package com.project.ccts.model.entities;

import com.project.ccts.model.enums.InstitutionType;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Institution extends SampleLocality {
    @OneToMany
    private Collection<UserCredential> userCredentials;
    private InstitutionType type;

    public Institution(){}

    public Institution(String name, Address address, String email, String cellPhone, InstitutionType type) {
        super(name, address, email, cellPhone);
        this.type = type;
    }


    public Institution(InstitutionType type) {
        this.type = type;
    }

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

    @Override
    public String toString() {
        return "Institution{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", email='" + email + '\'' +
                ", cellPhone='" + cellPhone + '\'' +
                '}';
    }
}
