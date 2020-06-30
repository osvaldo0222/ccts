package com.project.ccts.dto;

import com.project.ccts.model.Address;

import java.util.Collection;

public class LocalityDetailsDTO {
    private Long id;
    private String name;
    private Address address;
    private String email;
    private String cellPhone;
    private Collection<BeaconDetailsDTO> nodes;


    public LocalityDetailsDTO(Long id, String name, Address address, String email, String cellPhone, Collection<BeaconDetailsDTO> nodes) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.cellPhone = cellPhone;
        this.nodes = nodes;
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

    public Collection<BeaconDetailsDTO> getNodes() {
        return nodes;
    }

    public void setNodes(Collection<BeaconDetailsDTO> nodes) {
        this.nodes = nodes;
    }
}
