package com.project.ccts.model.entities;

import javax.persistence.*;

@Entity
public class NodeCredential extends Credential {
    @OneToOne(mappedBy = "nodeCredential")
    private Node node;

    public NodeCredential() {
    }

    public NodeCredential(String username, String password, Boolean authenticated) {
        super(username, password, authenticated);
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }
}
