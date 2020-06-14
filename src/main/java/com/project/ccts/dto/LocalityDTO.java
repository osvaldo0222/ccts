package com.project.ccts.dto;

import com.project.ccts.model.Address;

public class LocalityDTO {
    private Long id;
    private String name;
    private String province;
    private Integer visits;
    private Integer nodes;

    public LocalityDTO(Long id, String name, String province, Integer visits, Integer nodes) {
        this.id = id;
        this.name = name;
        this.province = province;
        this.visits = visits;
        this.nodes = nodes;
    }

    public Integer getNodes() {
        return nodes;
    }

    public void setNodes(Integer nodes) {
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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Integer getVisits() {
        return visits;
    }

    public void setVisits(Integer visits) {
        this.visits = visits;
    }
}
