package com.project.ccts.dto;

public class LocalityDTO {
    private Long id;
    private String name;
    private String province;
    private Integer nodes;

    public LocalityDTO(Long id, String name, String province, Integer nodes) {
        this.id = id;
        this.name = name;
        this.province = province;
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

    
}
