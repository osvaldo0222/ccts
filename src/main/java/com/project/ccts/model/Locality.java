package com.project.ccts.model;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Collection;

@Entity
public class Locality extends SampleLocality {
    @OneToMany(mappedBy = "locality")
    private Collection<Node> nodes;
    @OneToMany(mappedBy = "locality")
    private Collection<Visit> visits;

    public Collection<Node> getNodes() {
        return nodes;
    }

    public void setNodes(Collection<Node> nodes) {
        this.nodes = nodes;
    }

    public Collection<Visit> getVisits() {
        return visits;
    }

    public void setVisits(Collection<Visit> visits) {
        this.visits = visits;
    }
}
