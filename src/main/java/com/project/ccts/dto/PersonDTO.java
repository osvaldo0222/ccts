package com.project.ccts.dto;

import com.project.ccts.model.Address;

public class PersonDTO {
    private String firstName;
    private String lastName;
    private Address  address;
    private String occupation;
    private Integer age;
    private boolean status;

    public PersonDTO(){}
    public PersonDTO(String firstName, String lastName, Address address, String occupation, Integer age, boolean status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.occupation = occupation;
        this.age = age;
        this.status = status;
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
