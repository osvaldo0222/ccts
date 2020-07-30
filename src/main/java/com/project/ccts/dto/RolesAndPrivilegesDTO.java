package com.project.ccts.dto;

import java.util.Collection;

public class RolesAndPrivilegesDTO {
    private String name;
    private Collection<String> privileges;

    public RolesAndPrivilegesDTO(String name, Collection<String> privileges) {
        this.name = name;
        this.privileges = privileges;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<String> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Collection<String> privileges) {
        this.privileges = privileges;
    }
}
