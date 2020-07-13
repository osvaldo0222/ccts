package com.project.ccts.model.enums;

public enum NodeStatus {
    DOWN(1, "Apagado"), ACTIVE(2, "Activo"), INACTIVE(3, "Inactivo");

    private Integer id;
    private String status;

    NodeStatus(Integer id, String status) {
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
