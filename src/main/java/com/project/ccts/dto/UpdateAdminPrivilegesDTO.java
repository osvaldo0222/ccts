package com.project.ccts.dto;

import java.util.Collection;

public class UpdateAdminPrivilegesDTO {
    private String username;
    private String email;
    private Collection<String> tags;

    public UpdateAdminPrivilegesDTO(String username, String email, Collection<String> tags) {
        this.username = username;
        this.email = email;
        this.tags = tags;
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

    public Collection<String> getTags() {
        return tags;
    }

    public void setTags(Collection<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "UpdateAdminPrivilegesDTO{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + tags +
                '}';
    }
}
