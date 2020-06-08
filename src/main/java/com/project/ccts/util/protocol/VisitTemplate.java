package com.project.ccts.util.protocol;

import java.util.Collection;

public class VisitTemplate {
    private String nodeIdentifier;
    private Collection<TagTemplate> tagTemplates;

    public String getNodeIdentifier() {
        return nodeIdentifier;
    }

    public void setNodeIdentifier(String nodeIdentifier) {
        this.nodeIdentifier = nodeIdentifier;
    }

    public Collection<TagTemplate> getTagTemplates() {
        return tagTemplates;
    }

    public void setTagTemplates(Collection<TagTemplate> tagTemplates) {
        this.tagTemplates = tagTemplates;
    }
}
