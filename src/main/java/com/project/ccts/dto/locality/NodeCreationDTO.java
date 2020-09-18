package com.project.ccts.dto.locality;

public class NodeCreationDTO {
    private String name;
    private String email;
    private String nodeDescription;

    public NodeCreationDTO(String name, String email, String nodeDescription) {
        this.name = name;
        this.email = email;
        this.nodeDescription = nodeDescription;
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

    public String getNodeDescription() {
        return nodeDescription;
    }

    public void setNodeDescription(String nodeDescription) {
        this.nodeDescription = nodeDescription;
    }

    @Override
    public String toString() {
        return "NodeCreationDTO{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", Description='" + nodeDescription + '\'' +
                '}';
    }
}
