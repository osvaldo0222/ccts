package com.project.ccts.model.entities;

import com.project.ccts.model.enums.CivilStatus;
import com.project.ccts.model.enums.Gender;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String personalIdentifier;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String cellPhone;
    private LocalDate birthDate;
    private Gender gender;
    private CivilStatus civilStatus;
    private String occupation;
    @Embedded
    private Address address;
    @OneToOne
    private UserCredential userCredential;
    @OneToMany(mappedBy = "person")
    private Collection<Visit> visits;
    @CreationTimestamp
    private Date createDate;
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private Collection<HealthStatus> status;
    @OneToMany(mappedBy = "person")
    private Collection<PersonAndKInfectors> personAndKInfectors;


    public Person() { }

    public Person(String personalIdentifier, String firstName, String lastName, String email, String cellPhone, LocalDate birthDate, Gender gender, CivilStatus civilStatus, String occupation, Address address, UserCredential userCredential) {
        this.personalIdentifier = personalIdentifier;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.cellPhone = cellPhone;
        this.birthDate = birthDate;
        this.gender = gender;
        this.civilStatus = civilStatus;
        this.occupation = occupation;
        this.address = address;
        this.userCredential = userCredential;
    }

    public Person(String personalIdentifier, String firstName, String lastName, String email, String occupation, Address address, UserCredential userCredential) {
        this.personalIdentifier = personalIdentifier;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.occupation = occupation;
        this.address = address;
        this.userCredential = userCredential;
    }
    public Person(String personalIdentifier, String firstName, String lastName, String email, String occupation, Address address) {
        this.personalIdentifier = personalIdentifier;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.occupation = occupation;
        this.address = address;

    }
    public Person(String personalIdentifier, String firstName, String lastName, String email, String occupation, Address address,LocalDate birthDate, CivilStatus civilStatus, Gender gender) {
        this.personalIdentifier = personalIdentifier;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.occupation = occupation;
        this.address = address;
        this.birthDate = birthDate;
        this.civilStatus = civilStatus;
        this.gender = gender;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPersonalIdentifier() {
        return personalIdentifier;
    }

    public void setPersonalIdentifier(String personalIdentifier) {
        this.personalIdentifier = personalIdentifier;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public CivilStatus getCivilStatus() {
        return civilStatus;
    }

    public void setCivilStatus(CivilStatus civilStatus) {
        this.civilStatus = civilStatus;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public UserCredential getUserCredential() {
        return userCredential;
    }

    public void setUserCredential(UserCredential userCredential) {
        this.userCredential = userCredential;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Collection<HealthStatus> getStatus() {
        return status;
    }

    public void setStatus(Collection<HealthStatus> status) {
        this.status = status;
    }

    public Collection<Visit> getVisits() {
        return visits;
    }

    public void setVisits(Collection<Visit> visits) {
        this.visits = visits;
    }

    public Collection<PersonAndKInfectors> getPersonAndKInfectors() {
        return personAndKInfectors;
    }

    public void setPersonAndKInfectors(Collection<PersonAndKInfectors> personAndKInfectors) {
        this.personAndKInfectors = personAndKInfectors;
    }

    public void addHealthStatus(HealthStatus healthStatus){
        if (getStatus() == null){
            this.status = new ArrayList<>();
        }
        getStatus().add(healthStatus);
    }
}
