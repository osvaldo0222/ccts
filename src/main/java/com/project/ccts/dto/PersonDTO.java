package com.project.ccts.dto;

import com.project.ccts.model.entities.Address;

import java.time.LocalDate;

public class PersonDTO {
    private String personalIdentifier;
    private String firstName;
    private String lastName;
    private Address  address;
    private String occupation;
    private Integer age;
    private boolean status;
    private String email;
    private LocalDate birthDate;
    private String civilStatus;
    private String gender;


    public PersonDTO(){}

    public PersonDTO(String personalIdentifier, String firstName, String lastName, Address address, String occupation, Integer age, boolean status, String email) {
        this.personalIdentifier = personalIdentifier;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.occupation = occupation;
        this.age = age;
        this.status = status;
        this.email = email;
    }
    public PersonDTO(  String firstName, String lastName, Address address, String occupation, Integer age, boolean status, String email) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.occupation = occupation;
        this.age = age;
        this.status = status;
        this.email = email;
    }

    public PersonDTO( String firstName, String lastName, Address address, String occupation,
                      Integer age, boolean status, String email, LocalDate birthDate, String civilStatus, String gender) {
        this.personalIdentifier = personalIdentifier;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.occupation = occupation;
        this.age = age;
        this.status = status;
        this.email = email;
        this.birthDate = birthDate;
        this.civilStatus = civilStatus;
        this.gender = gender;
    }

    public String getCivilStatus() {
        return civilStatus;
    }

    public void setCivilStatus(String civilStatus) {
        this.civilStatus = civilStatus;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public String getPersonalIdentifier() {
        return personalIdentifier;
    }

    public void setPersonalIdentifier(String personalIdentifier) {
        this.personalIdentifier = personalIdentifier;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
