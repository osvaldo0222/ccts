package com.project.ccts.service;

import com.project.ccts.model.Node;
import com.project.ccts.model.Tag;
import com.project.ccts.model.Visit;
import com.project.ccts.repository.VisitRepository;
import com.project.ccts.service.common.AbstractCrud;
import com.project.ccts.dto.NodeSubmitVisitDTO;
import com.project.ccts.util.logger.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * Create and add the visits of the tags that comes from a node.
     *
     * @param visits - Data transfer object of the visits
     * @throws Exception if the node identifier can't be found
     */
    public void addVisits(NodeSubmitVisitDTO visits) throws Exception {
        //Collection of bussiness logic visit entity
        List<Visit> visitsEntity = new ArrayList<>();

        //Getting the node from the visits came
        Node node = nodeService.findByNodeIdentifier(visits.getNodeIdentifier());

        if (node == null) {
            throw new Exception(String.format("Node with identifer %s not found", visits.getNodeIdentifier()));
        }

        //Looping the tags dto and creating the new visit
        visits.getTags().forEach(tagDTO -> {
            //Finding the real tag
            Tag tag = tagService.findByTagId(tagDTO.getTagId());

            if (tag != null) {
                visitsEntity.add(new Visit(tag, tagDTO.getAccessDate(), node.getLocality()));
            } else {
                Logger.getInstance().getLog(getClass()).warn(String.format("Tag with identifier %s not found", tagDTO.getTagId()));
            }
        });

        //Create all the visits
        createAll(visitsEntity);
    }
}
