package com.project.ccts.model;

import com.project.ccts.util.enums.CivilStatus;
import com.project.ccts.util.enums.Gender;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String personalIdentifier;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    private String cellPhone;
    private Date birthDate;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private CivilStatus civilStatus;
    private String occupation;
    @Embedded
    private Address address;
    @OneToOne
    private UserCredential userCredential;
    @OneToOne
    private Tag tag;
    @CreationTimestamp
    private Date tagGivenOn;
    @CreationTimestamp
    private Date createDate;
    @OneToMany(mappedBy = "person")
    private Collection<HealthStatus> status;

    public Person() { }

    public Person(String personalIdentifier, String firstName, String lastName, String email, String cellPhone, Date birthDate, Gender gender, CivilStatus civilStatus, String occupation, Address address, UserCredential userCredential, Tag tag) {
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
        this.tag = tag;
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
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

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public Date getTagGivenOn() {
        return tagGivenOn;
    }

    public void setTagGivenOn(Date tagGivenOn) {
        this.tagGivenOn = tagGivenOn;
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
}
