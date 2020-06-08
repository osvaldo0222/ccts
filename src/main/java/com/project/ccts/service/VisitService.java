package com.project.ccts.service;

import com.project.ccts.model.Node;
import com.project.ccts.model.Visit;
import com.project.ccts.repository.VisitRepository;
import com.project.ccts.service.common.AbstractCrud;
import com.project.ccts.util.protocol.TagTemplate;
import com.project.ccts.util.protocol.VisitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class VisitService extends AbstractCrud<Visit, Long> {

    private VisitRepository visitRepository;
    private NodeService nodeService;
    private TagService tagService;

    @Autowired
    public void setVisitRepository(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @Autowired
    public void setNodeService(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    @Autowired
    public void setTagService(TagService tagService) {
        this.tagService = tagService;
    }

    @Override
    public JpaRepository<Visit, Long> getDao() {
        return visitRepository;
    }

    public void addVisits(VisitTemplate body) {
        List<Visit> visits = new ArrayList<>();

        Node node = nodeService.findByNodeIdentifier(body.getNodeIdentifier());

        for (TagTemplate tag : body.getTagTemplates()) {
            Visit visit = new Visit();
            visit.setLocality(node.getLocality());
            visit.setTag(tagService.findByTagId(tag.getTagId()));
            visit.setAccessDate(tag.getAccessDate());
            visits.add(visit);
        }

        createAll(visits);
    }
}
