package com.project.ccts.model.enums;

public enum CivilStatus {
    MARRIED(1, "Casado/a"), SINGLE(2, "Soltero/a");

    private Integer id;
    private String status;

    CivilStatus(Integer id, String status) {
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
