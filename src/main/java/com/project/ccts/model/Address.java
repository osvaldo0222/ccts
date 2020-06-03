package com.project.ccts.model;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
    private String direction;
    private String postalCode;
    private String city;
    private String country;

    public Address() { }

    public Address(String direction, String postalCode, String city, String country) {
        this.direction = direction;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
