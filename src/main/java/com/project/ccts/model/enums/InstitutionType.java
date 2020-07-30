package com.project.ccts.model.enums;

public enum InstitutionType {
    GOVERNMENTAL(1, "Guvernamental"), HEALTH(2, "Salud"),
    COMMERCIAL(3, "Comercial"), EDUCATION(4, "Educacion"),
    SERVICE(5, "Servicios");

    private Integer id;
    private String type;

    InstitutionType(Integer id, String type) {
        this.id = id;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
