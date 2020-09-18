package com.project.ccts.model.enums;

public enum Gender {
    FEMALE(1, "Femenino"), MALE(2, "Masculino");

    private Integer id;
    private String status;

    Gender(Integer id, String status) {
        this.id = id;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}
