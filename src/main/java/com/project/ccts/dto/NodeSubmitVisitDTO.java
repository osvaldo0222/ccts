package com.project.ccts.dto;

import java.util.Collection;

public class NodeSubmitVisitDTO {
    private String nodeIdentifier;
    private Collection<NodeSubmitTagDTO> tags;

    public String getNodeIdentifier() {
        return nodeIdentifier;
    }

    public void setNodeIdentifier(String nodeIdentifier) {
        this.nodeIdentifier = nodeIdentifier;
    }

    public Collection<NodeSubmitTagDTO> getTags() {
        return tags;
    }

    public void setTags(Collection<NodeSubmitTagDTO> tags) {
        this.tags = tags;
    }
}
