package com.project.ccts.dto;

import com.project.ccts.model.Privilege;
import com.project.ccts.model.Role;

import java.util.ArrayList;
import java.util.Collection;

public class PersonCredentialListDTO {
    private Long id;
    private String userName;
    private boolean isAuthenticated;
    private Collection<String> privileges;
    private String name;
    private String email;

    public PersonCredentialListDTO(Long id,String userName, boolean isAuthenticated, Collection<String> privileges) {
        this.id = id;
        this.userName = userName;
        this.isAuthenticated = isAuthenticated;
        this.privileges = privileges;
    }

    public PersonCredentialListDTO(Long id, String userName, boolean isAuthenticated, Collection<String> privileges, String name, String email) {
        this.id = id;
        this.userName = userName;
        this.isAuthenticated = isAuthenticated;
        this.privileges = privileges;
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }

    public Collection<String> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Collection<String> privileges) {
        this.privileges = privileges;
    }
}
