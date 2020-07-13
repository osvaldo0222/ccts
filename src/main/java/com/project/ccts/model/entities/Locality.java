package com.project.ccts.model.entities;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class Locality extends SampleLocality {
    @OneToMany(mappedBy = "locality",cascade = CascadeType.ALL)
    private Collection<Node> nodes;
    @Embedded
    private GpsLocation gpsLocation;

    public Locality(String name, Address address, String email, String cellPhone) {
        super(name, address, email, cellPhone);
    }

    public Locality(){
        super();
    }

    public Collection<Node> getNodes() {
        return nodes;
    }

    public void setNodes(Collection<Node> nodes) {
        this.nodes = nodes;
    }

    public GpsLocation getGpsLocation() {
        return gpsLocation;
    }

    public void setGpsLocation(GpsLocation gpsLocation) {
        this.gpsLocation = gpsLocation;
    }
}
