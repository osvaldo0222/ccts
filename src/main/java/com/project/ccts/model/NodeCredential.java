package com.project.ccts.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.ccts.util.enums.NodeStatus;

import javax.persistence.*;

@Entity
public class NodeCredential extends Credential {
    @Enumerated(EnumType.STRING)
    private NodeStatus status;
    @OneToOne(mappedBy = "nodeCredential")
    @JsonIgnore
    private Node node;

    public NodeStatus getStatus() {
        return status;
    }

    public void setStatus(NodeStatus status) {
        this.status = status;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }
}
