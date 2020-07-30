package com.project.ccts.dto;

import com.project.ccts.model.entities.Role;

import java.util.Collection;

public class AdminListDTO {
    private String email;
    private String username;
    private Collection<String> roles;
    private boolean isAuthenticated;

    public AdminListDTO(String email, String username, Collection<String> roles, boolean isAuthenticated) {
        this.email = email;
        this.username = username;
        this.roles = roles;
        this.isAuthenticated = isAuthenticated;
    }
    public AdminListDTO(String email, String username, Collection<String> roles) {
        this.email = email;
        this.username = username;
        this.roles = roles;
     }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Collection<String> getPrivileges() {
        return roles;
    }

    public void setPrivileges(Collection<String> privileges) {
        this.roles = privileges;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }
}
