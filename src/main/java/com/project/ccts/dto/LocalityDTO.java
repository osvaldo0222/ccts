package com.project.ccts.dto;

import com.project.ccts.model.Address;

public class LocalityDTO {
    private Long id;
    private String name;
    private Address address;
    private Integer visits;

    public LocalityDTO(Long id, String name, Address address, Integer visits) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.visits = visits;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Integer getVisits() {
        return visits;
    }

    public void setVisits(Integer visits) {
        this.visits = visits;
    }
}
