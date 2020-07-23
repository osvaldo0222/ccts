package com.project.ccts.dto;

import java.util.Collection;

public class UpdateAdminPrivilegesDTO {
    private String username;
    private String email;
    private Collection<String> privileges;

    public UpdateAdminPrivilegesDTO(String username, String email, Collection<String> privileges) {
        this.username = username;
        this.email = email;
        this.privileges = privileges;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<String> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Collection<String> privileges) {
        this.privileges = privileges;
    }
}
