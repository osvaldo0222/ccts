package com.project.ccts.dto;

import java.math.BigDecimal;

public class NewLocalityDTO {
    private String cellPhone;
    private String name;
    private String email;
    private String direction;
    private String postalCode;
    private String city;
    private Double latitude;
    private Double longitude;
    private String type;

    public NewLocalityDTO(){}
    public NewLocalityDTO(String cellPhone, String name, String email, String direction, String postalCode, String city, Double latitude, Double longitude) {
        this.cellPhone = cellPhone;
        this.name = name;
        this.email = email;
        this.direction = direction;
        this.postalCode = postalCode;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public NewLocalityDTO(String cellPhone, String name, String email, String direction, String postalCode, String city, Double latitude, Double longitude, String type) {
        this.cellPhone = cellPhone;
        this.name = name;
        this.email = email;
        this.direction = direction;
        this.postalCode = postalCode;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
    }

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
