package com.project.ccts.model;

import javax.persistence.*;

@MappedSuperclass
public abstract class SampleLocality {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String name;
    @Embedded
    protected Address address;
    @Column(unique = true)
    protected String email;
    protected String cellPhone;

    public SampleLocality(String name, Address address, String email, String cellPhone) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.cellPhone = cellPhone;
    }

    public SampleLocality() {

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
}
